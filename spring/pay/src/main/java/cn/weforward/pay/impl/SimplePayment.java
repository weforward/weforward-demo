package cn.weforward.pay.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.weforward.common.NameItem;
import cn.weforward.common.util.StringUtil;
import cn.weforward.data.UniteId;
import cn.weforward.data.persister.Persistent;
import cn.weforward.data.persister.Persister;
import cn.weforward.data.persister.Reloadable;
import cn.weforward.data.persister.support.AbstractPersistent;
import cn.weforward.pay.Form;
import cn.weforward.pay.PayException;
import cn.weforward.pay.di.PayDi;
import cn.weforward.protocol.client.EventInvoker;
import cn.weforward.protocol.support.datatype.SimpleDtObject;

/**
 * 简单的支付单实现
 * 
 * @author daibo
 *
 */
public class SimplePayment extends AbstractPersistent<PayDi> implements PaymentExt, Reloadable<SimplePayment> {

	private static final Logger _Logger = LoggerFactory.getLogger(SimplePayment.class);

	@Resource
	protected PaymentVo m_Vo;

	protected SimplePayment(PayDi di) {
		super(di);
	}

	public SimplePayment(PayDi di, int amount, int timeout) {
		super(di);
		m_Vo = new PaymentVo();
		m_Vo.amount = amount;
		m_Vo.timeout = timeout;
		m_Vo.createTime = new Date();
		m_Vo.channels = Collections.emptyList();
		m_Vo.state = STATE_NEW.id;
		persistenceUpdateNow();
	}

	protected PaymentVo getVo() {
		return m_Vo;
	}

	@Override
	public String getId() {
		return getPersistenceId().getOrdinal();
	}

	@Override
	public int getAmount() {
		return getVo().amount;
	}

	@Override
	public int getTimeout() {
		return getVo().timeout;
	}

	private boolean isState(NameItem ni) {
		return ni.id == getVo().state;
	}

	private void setState(NameItem state) {
		getVo().state = state.id;
		markPersistenceUpdate();
	}

	@Override
	public void setCallback(String callback) {
		if (StringUtil.eq(getVo().callback, callback)) {
			return;
		}
		getVo().callback = callback;
		markPersistenceUpdate();
	}

	@Override
	public String getCallback() {
		return getVo().callback;
	}

	@Override
	public void setUser(String user) {
		if (StringUtil.eq(getVo().user, user)) {
			return;
		}
		getVo().user = user;
		markPersistenceUpdate();
	}

	@Override
	public String getUser() {
		return getVo().user;
	}

	@Override
	public PayProvider getProvider(String providerName) {
		return getBusinessDi().getProvider(providerName);
	}

	@Override
	public synchronized Form submit(String channelName) throws PayException {
		if (isState(STATE_NEW)) {
			getVo().state = STATE_SUBMIT.id;
			markPersistenceUpdate();
		} else if (!isState(STATE_SUBMIT)) {
			throw new PayException("当前状态[" + getState() + "]不允许提交");
		}
		PayChannel channel = openChannel(channelName);
		Form request = channel.request();
		getVo().submitTime = new Date();
		markPersistenceUpdate();
		return request;
	}

	@Override
	public synchronized void notifyPay(Form form) throws PayException {
		for (PayChannel channel : getChannels()) {
			if (channel.result(form)) {
				return;
			}
		}
	}

	@Override
	public synchronized void cancel() throws PayException {
		if (!isState(STATE_SUBMIT)) {
			throw new PayException("不能取消未提交的支付单");
		}
		if (isState(STATE_CANCEL)) {
			return;
		}
		PayException error = null;
		for (PayChannel channel : getChannels()) {
			try {
				channel.cancel();
			} catch (PayException e) {
				error = e;
				_Logger.warn("取消异常," + getId(), e);
			}
		}
		if (null != error) {
			throw error;
		}
		setState(STATE_CANCEL);
	}

	@Override
	public String check() throws PayException {
		StringBuilder sb = null;
		List<PayChannel> channels = getChannels();
		for (PayChannel ph : channels) {
			if (null == sb) {
				sb = new StringBuilder();
			} else {
				sb.append(";");
			}
			sb.append(ph.getName());
			try {
				NameItem status = ph.check();
				if (null == status) {
					sb.append("[空]");
				} else {
					sb.append("[").append(status.name).append("]");
				}
			} catch (Exception e) {
				sb.append(":").append(String.valueOf(e));
				_Logger.warn(ph.toString(), e);
			}
		}
		return sb.toString();
	}

	@Override
	public NameItem getState() {
		return STATES.get(getVo().state);
	}

	@Override
	public Date getCreateTime() {
		return getVo().createTime;
	}

	@Override
	public boolean onFinished(NameItem state, PayChannel channel, String reason) {
		synchronized (this) {
			if (!iDo()) {
				// 当前环境下不允许有业务动作（原因是在分布状态下此支付单不属于当前服务器负责）
				_Logger.warn("非控支付单业务操作：" + this);
				return false;
			}
			if (getState().id == STATE_SUCCESS.id) {
				return false;// 状态是成功的就不再改变了
			}
			getVo().finishedTime = new Date();
			markPersistenceUpdate();
			setState(state);
		}
		try {
			if (STATE_SUCCESS.id == state.id) {
				onPaySuccess();
			} else {
				onPayFail(reason);
			}
		} catch (Exception e) {
			_Logger.error("回调通知异常," + getId(), e);
		}
		return true;
	}

	private void onPaySuccess() {
		String uri = getVo().callback;
		EventInvoker invoker = getBusinessDi().getEventInvoker();
		if (!StringUtil.isEmpty(uri) && null != invoker) {
			SimpleDtObject params = new SimpleDtObject();
			params.put("id", getId());
			invoker.invoke("onPaySuccess", uri, params);
		}
	}

	private void onPayFail(String reason) {
		String uri = getVo().callback;
		EventInvoker invoker = getBusinessDi().getEventInvoker();
		if (!StringUtil.isEmpty(uri) && null != invoker) {
			SimpleDtObject params = new SimpleDtObject();
			params.put("id", getId());
			params.put("reason", reason);
			invoker.invoke("onPayFail", uri, params);
		}
	}

	private PayChannel openChannel(String name) {
		for (PayChannel channel : getChannels()) {
			if (StringUtil.eq(channel.getName(), name)) {
				return channel;
			}
		}
		synchronized (this) {
			PayProvider provider = getBusinessDi().findProvider(name);
			if (null == provider) {
				throw new UnsupportedOperationException("不支持的支付渠道[" + name + "]");
			}
			PayChannel newchannel = new PayChannel(name, provider.getName(), this);
			List<PayChannel> list = new ArrayList<>();
			for (PayChannel channel : getChannels()) {
				if (StringUtil.eq(channel.getName(), name)) {
					return channel;
				}
				list.add(channel);
			}
			list.add(newchannel);
			getVo().channels = list;
			markPersistenceUpdate();
			return newchannel;
		}

	}

	@Override
	public void onAfterReflect(Persister<? extends Persistent> persister, UniteId id, String version, String driveIt) {
		super.onAfterReflect(persister, id, version, driveIt);
		for (PayChannel channel : getChannels()) {
			channel.bindPayment(this);
		}
	}

	/** 支付渠道 */
	public List<PayChannel> getChannels() {
		List<PayChannel> list = getVo().channels;
		return null == list ? Collections.emptyList() : list;
	}

	@Override
	public boolean onReloadAccepted(Persister<SimplePayment> persister, SimplePayment other) {
		m_Vo = other.m_Vo;
		return true;
	}

}
