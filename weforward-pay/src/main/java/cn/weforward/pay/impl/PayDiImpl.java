package cn.weforward.pay.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.weforward.data.persister.Persistent;
import cn.weforward.data.persister.Persister;
import cn.weforward.data.persister.PersisterFactory;
import cn.weforward.pay.di.PayDi;
import cn.weforward.protocol.client.EventInvoker;

/**
 * 支付依赖di实现
 * 
 * @author daibo
 *
 */
public class PayDiImpl implements PayDi {
	/** 日志 */
	static final Logger _Logger = LoggerFactory.getLogger(PayDiImpl.class);
	/** 持久类工厂 */
	protected PersisterFactory m_Factory;
	/** 支付单持久类 */
	protected Persister<SimplePayment> m_PsPayment;
	/** 支付供应商 */
	protected ConcurrentMap<String, PayProvider> m_Providers;
	/** 事件调用 */
	protected EventInvoker m_EventInvoker;

	public PayDiImpl(PersisterFactory factory) {
		m_Factory = factory;
		m_Providers = new ConcurrentHashMap<>();
		m_PsPayment = m_Factory.createPersister(SimplePayment.class, this);
	}

	public void setEventInvoker(EventInvoker invoker) {
		m_EventInvoker = invoker;
	}

	@Override
	public <E extends Persistent> Persister<E> getPersister(Class<E> clazz) {
		return m_Factory.getPersister(clazz);
	}

	@Override
	public void register(PayProvider provider) {
		PayProvider old = m_Providers.putIfAbsent(provider.getName(), provider);
		if (null != old) {
			_Logger.warn("拒绝同名的供应商:" + provider.getName());
		}
	}

	@Override
	public void unRegister(PayProvider provider) {
		m_Providers.remove(provider.getName());
	}

	@Override
	public PayProvider findProvider(String name) {
		for (Map.Entry<String, PayProvider> p : m_Providers.entrySet()) {
			String providername = p.getKey();
			if (name.startsWith(providername)) {
				return p.getValue();
			}
		}
		return null;
	}

	@Override
	public PayProvider getProvider(String providerName) {
		return m_Providers.get(providerName);
	}

	@Override
	public EventInvoker getEventInvoker() {
		return m_EventInvoker;
	}

}
