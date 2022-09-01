package cn.weforward.order;

import java.util.List;

import cn.weforward.common.NameItem;
import cn.weforward.common.NameItems;
import cn.weforward.common.ResultPage;
import cn.weforward.data.UniteId;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.order.kit.PayException;

/**
 * 订单
 * 
 * @author daibo
 *
 */
public interface Order {
	/** 状态-新建 */
	NameItem STATE_NEW = NameItem.valueOf("新建", 0);
	/** 状态-提交失败 */
	NameItem STATE_SUBMIT_FAILED = NameItem.valueOf("提交失败", 1);
	/** 状态-等待付款 */
	NameItem STATE_WAITPAY = NameItem.valueOf("等待付款", 2);
	/** 状态-订单关闭 */
	NameItem STATE_CLOSE = NameItem.valueOf("订单关闭", 3);
	/** 状态-完成失败 */
	NameItem STATE_COMPLETE_FAILED = NameItem.valueOf("完成失败", 4);
	/** 状态-交易成功 */
	NameItem STATE_SUCCESS = NameItem.valueOf("交易成功", 5);
	/** 状态-全部 */
	NameItems STATES = NameItems.valueOf(STATE_NEW, STATE_SUBMIT_FAILED, STATE_WAITPAY, STATE_CLOSE,
			STATE_COMPLETE_FAILED, STATE_SUCCESS);

	/**
	 * id
	 * 
	 * @return
	 */
	UniteId getId();

	/**
	 * 金额
	 * 
	 * @return
	 */
	int getAmount();

	/**
	 * 交易列表
	 * 
	 * @return
	 */
	List<Trade> getTrades();

	/**
	 * 提交订单
	 * 
	 * @param goodslist
	 * @throws OrderException
	 */
	void submit(List<? extends WantGoods> goodslist) throws OrderException;

	/**
	 * 支付
	 * 
	 * @param channel
	 * @return
	 * @throws PayException
	 */
	Form pay(String channel) throws OrderException, PayException;

	/**
	 * 确认支付
	 * 
	 * @param password
	 * @throws PayException
	 */
	void confirmPay(String password) throws PayException;

	/**
	 * 取消
	 * 
	 * @throws OrderException
	 */
	void cancel() throws OrderException;

	/**
	 * 支付单号
	 * 
	 * @return
	 */
	String getPayno();

	/**
	 * 状态
	 * 
	 * @return
	 */
	NameItem getState();

	/**
	 * 获取日志
	 *
	 */
	ResultPage<BusinessLog> getLogs();

	/**
	 * 支付成功
	 */
	void onPaySuccess();

	/**
	 * 支付失败
	 */
	void onPayFail();

}
