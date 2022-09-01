package cn.weforward.order.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.weforward.common.NameItem;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.StringUtil;
import cn.weforward.common.util.TaskExecutor;
import cn.weforward.data.UniteId;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.data.persister.ext.ConditionUtil;
import cn.weforward.data.persister.support.AbstractPersistent;
import cn.weforward.order.Form;
import cn.weforward.order.Order;
import cn.weforward.order.OrderException;
import cn.weforward.order.Trade;
import cn.weforward.order.WantGoods;
import cn.weforward.order.di.OrderDi;
import cn.weforward.order.kit.CreateParam;
import cn.weforward.order.kit.PayException;
import cn.weforward.order.kit.PayMethods;
import cn.weforward.order.kit.PayParam;
import cn.weforward.order.kit.PaymentView;
import cn.weforward.order.kit.ProductView;
import cn.weforward.order.kit.TradeException;
import cn.weforward.order.kit.TradeMethods;
import cn.weforward.order.kit.TradeParam;
import cn.weforward.order.kit.TradeView;
import cn.weforward.protocol.client.util.IdBean;

/**
 * 订单实现
 * 
 * @author daibo
 *
 */
public class SimpleOrder extends AbstractPersistent<OrderDi> implements Order {
	/** 日志 */
	private static final Logger _Logger = LoggerFactory.getLogger(SimpleOrder.class);
	/** 订单超时时间 */
	private static final int TIMEOUT = 15 * 60 * 1000;
	/** 用户属性 */
	public static final String USER_FIELD = ConditionUtil.field("m_Vo", "user");
	/** 支付单属性 */
	public static final String PAYMENT_FIELD = ConditionUtil.field("m_Vo", "payment");

	@Resource
	protected OrderVo m_Vo;

	protected SimpleOrder(OrderDi di) {
		super(di);
	}

	public SimpleOrder(OrderDi di, String user) {
		super(di);
		OrderVo vo = new OrderVo();
		vo.user = user;
		vo.createTime = new Date();
		m_Vo = vo;
		persistenceUpdateNow();
	}

	public int getTimeout() {
		long s = System.currentTimeMillis() - getVo().createTime.getTime();
		long v = (TIMEOUT - s);
		if (v <= 0) {
			return 0;
		} else {
			return (int) v;
		}
	}

	@Override
	public UniteId getId() {
		return getPersistenceId();
	}

	@Override
	public int getAmount() {
		return getVo().amount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Trade> getTrades() {
		List<? extends Trade> list = getVo().trades;
		return (List<Trade>) list;
	}

	@Override
	public synchronized void submit(List<? extends WantGoods> goodslist) throws OrderException {
		if (!isState(STATE_NEW)) {
			throw new OrderException("当前状态[" + getState() + "]不能提交");
		}
		if (isState(STATE_WAITPAY)) {
			return;
		}
		boolean isOk = false;
		try {
			doSubmit(goodslist);
			isOk = true;
		} catch (TradeException e) {
			throw new OrderException("提交订单失败，交易异常:" + e.getMessage(), e);
		} finally {
			if (isOk) {
				onSubmitSuccess();
			} else {
				onSubmitFailed();
			}
		}

	}

	private void onSubmitSuccess() {
		getBusinessDi().writeLog(getId(), "submit", "订单提交成功", null);
		setState(STATE_WAITPAY);
		watch();
	}

	private void watch() {
		getBusinessDi().getTaskExecutor().execute(new Runnable() {

			@Override
			public void run() {
				try {
					checkPay();
					if (isState(STATE_WAITPAY)) {
						getBusinessDi().writeLog(getId(), "watch", "订单超时，自动取消", null);
						cancel();
					}
				} catch (OrderException e) {
					_Logger.error("取消订单异常," + toString(), e);
				}
			}
		}, TaskExecutor.OPTION_NONE, getTimeout());

	}

	private void onSubmitFailed() {
		getBusinessDi().writeLog(getId(), "submit", "订单提交失败", null);
		setState(STATE_SUBMIT_FAILED);
		TradeMethods methods = getBusinessDi().getTradeMethods();
		List<TradeVo> trades = new ArrayList<>();
		for (TradeVo trade : getVo().trades) {
			try {
				trades.add(toTradeVo(methods.unlock(IdBean.valueOf(trade.getId()))));
			} catch (Throwable e) {
				trades.add(trade);
				// 这里可考虑自动重试或报警机制
				_Logger.error("解锁交易异常," + toString(), e);
			}
		}
		getVo().trades = trades;
		markPersistenceUpdate();
	}

	/*
	 * 提交订单
	 */
	private void doSubmit(List<? extends WantGoods> goodslist) throws OrderException, TradeException {
		TradeMethods methods = getBusinessDi().getTradeMethods();
		List<TradeVo> createTrades = new ArrayList<>();
		int amount = 0;
		for (WantGoods goods : goodslist) {
			TradeParam params = new TradeParam();
			params.setNum(goods.getNum());
			params.setProductId(goods.getGoods());
			TradeView view = methods.create(params);
			TradeVo trade = toTradeVo(view);
			createTrades.add(trade);
			amount += trade.getAmount();
		}
		getVo().amount = amount;
		getVo().trades = createTrades;
		markPersistenceUpdate();
		List<TradeVo> locktrades = new ArrayList<>();
		for (Trade trade : createTrades) {
			locktrades.add(toTradeVo(methods.lock(IdBean.valueOf(trade.getId()))));
		}
		getVo().trades = createTrades;
		markPersistenceUpdate();
	}

	private static TradeVo toTradeVo(TradeView trade) {
		TradeVo vo = new TradeVo();
		vo.id = trade.getId();
		vo.amount = trade.getAmount();
		vo.num = trade.getNum();
		ProductView product = trade.getProduct();
		GoodsVo goods = new GoodsVo();
		goods.id = product.getId();
		goods.name = product.getName();
		goods.price = product.getPrice();
		vo.goods = goods;
		return vo;
	}

	@Override
	public synchronized Form pay(String channel) throws OrderException, PayException {
		String payment = getVo().payment;
		PayMethods methods = getBusinessDi().getPayMethods();
		if (StringUtil.isEmpty(payment)) {
			CreateParam params = new CreateParam();
			params.setAmount(getAmount());
			params.setUser(getVo().user);
			String callback = getBusinessDi().getOrderCallback();
			params.setCallback(callback);
			int timeout = (int) (getTimeout() - (System.currentTimeMillis() - getVo().createTime.getTime()));
			params.setTimeout(timeout);
			PaymentView paymentView = methods.create(params);
			payment = paymentView.getId();
			getVo().payment = payment;
			persistenceUpdateNow();
		}
		PayParam params = new PayParam();
		params.setId(payment);
		params.setChannel(channel);
		return new Form(methods.submit(params));
	}

	@Override
	public void confirmPay(String password) throws PayException {
		if (!StringUtil.eq(password, "666666")) {
			throw new PayException("密码错误");
		}
		String payment = getVo().payment;
		PayMethods methods = getBusinessDi().getPayMethods();
		methods.cashReceived(IdBean.valueOf(payment));
		markPersistenceUpdate();
	}

	@Override
	public void cancel() throws OrderException {
		if (!isState(STATE_WAITPAY)) {
			throw new OrderException("当前状态[" + getState() + "]不能取消");
		}
		getBusinessDi().writeLog(getId(), "cancel", "订单关闭", null);
		TradeMethods methods = getBusinessDi().getTradeMethods();
		List<TradeVo> trades = new ArrayList<>();
		for (TradeVo trade : getVo().trades) {
			try {
				trades.add(toTradeVo(methods.unlock(IdBean.valueOf(trade.getId()))));
			} catch (Throwable e) {
				trades.add(trade);
				// 这里可考虑自动重试或报警机制
				_Logger.error("解锁交易异常," + toString(), e);
			}
		}
		getVo().trades = trades;
		setState(STATE_CLOSE);
	}

	@Override
	public NameItem getState() {
		return STATES.get(getVo().state);
	}

	@Override
	public ResultPage<BusinessLog> getLogs() {
		return getBusinessDi().getLogs(getId());
	}

	protected OrderVo getVo() {
		return m_Vo;
	}

	protected boolean isState(NameItem state) {
		return getVo().state == state.id;
	}

	protected void setState(NameItem state) {
		getVo().state = state.id;
		markPersistenceUpdate();
	}

	protected synchronized void complete() throws OrderException {
		if (!isState(STATE_WAITPAY) && !isState(STATE_COMPLETE_FAILED)) {
			throw new OrderException("当前状态[" + getState() + "]不能提交");
		}
		boolean isOk = false;
		try {
			doComplete();
			isOk = true;
		} catch (TradeException e) {
			throw new OrderException("提交订单失败，交易异常:" + e.getMessage(), e);
		} finally {
			if (isOk) {
				onCompleteSuccess();
			} else {
				onCompleteFailed();
			}
		}
	}

	/*
	 * 提交订单
	 */
	private void doComplete() throws OrderException, TradeException {
		TradeMethods methods = getBusinessDi().getTradeMethods();
		List<TradeVo> trades = new ArrayList<>();
		List<TradeVo> olds = getVo().trades;
		for (Trade trade : olds) {
			try {
				trades.add(toTradeVo(methods.lock(IdBean.valueOf(trade.getId()))));
			} catch (TradeException e) {
				getBusinessDi().writeLog(getId(), "complete", "完成" + trade.getId() + "异常", e.getMessage());
				throw e;
			}
		}
		getVo().trades = trades;
		markPersistenceUpdate();
	}

	private void onCompleteSuccess() {
		getBusinessDi().writeLog(getId(), "submit", "订单完成成功", null);
		setState(STATE_SUCCESS);
	}

	private void onCompleteFailed() {
		getBusinessDi().writeLog(getId(), "submit", "订单完成失败", null);
		setState(STATE_COMPLETE_FAILED);
	}

	@Override
	public String getPayno() {
		return getVo().payment;
	}

	@Override
	public void onPaySuccess() {
		checkPay();
	}

	@Override
	public void onPayFail() {
		checkPay();
	}

	private void checkPay() {
		if (!isState(STATE_WAITPAY)) {
			return;
		}
		PaymentView view = getBusinessDi().getPayMethods().check(IdBean.valueOf(getVo().payment));
		switch (view.getState()) {
		case 3:
			getBusinessDi().writeLog(getId(), "onPaySuccess", "支付成功，完成订单", null);
			try {
				complete();
			} catch (OrderException e) {
				_Logger.error("支付异常，完成订单异常," + toString(), e);
			}
			break;
		case 2:
			getBusinessDi().writeLog(getId(), "onPayFail", "支付失败，取消订单", null);
			try {
				cancel();
			} catch (OrderException e) {
				_Logger.error("支付失败，取消订单异常," + toString(), e);
			}
			break;
		default:
			break;
		}

	}

	public void reinit() {
		if (isState(STATE_WAITPAY)) {
			watch();
		}
	}

}
