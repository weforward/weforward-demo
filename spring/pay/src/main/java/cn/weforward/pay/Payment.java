package cn.weforward.pay;

import java.util.Date;

import cn.weforward.common.NameItem;
import cn.weforward.common.NameItems;

/**
 * 支付单
 * 
 * @author daibo
 *
 */
public interface Payment {
	/** 状态-初始 */
	NameItem STATE_NEW = NameItem.valueOf("初始", 0);
	/** 状态-已提交 */
	NameItem STATE_SUBMIT = NameItem.valueOf("已提交", 1);
	/** 状态-已取消 */
	NameItem STATE_CANCEL = NameItem.valueOf("已取消", 2);
	/** 状态-成功 */
	NameItem STATE_SUCCESS = NameItem.valueOf("成功", 3);
	/** 状态-全部 */
	NameItems STATES = NameItems.valueOf(STATE_NEW, STATE_SUBMIT, STATE_CANCEL, STATE_SUCCESS);

	/**
	 * 支付单
	 * 
	 * @return
	 */
	String getId();

	/**
	 * 金额（单位分）
	 * 
	 * @return
	 */
	int getAmount();

	/**
	 * 超时时间(单位秒)
	 * 
	 * @return
	 */
	int getTimeout();

	/**
	 * 生成支付表单
	 * 
	 * @param channel 渠道，如 微信/支付宝/现金
	 * @return 表单集
	 */
	Form submit(String channel) throws PayException;

	/**
	 * 支付结果通知
	 * 
	 * @param form
	 * @throws PayException
	 */
	void notifyPay(Form form) throws PayException;

	/**
	 * 取消支付
	 */
	void cancel() throws PayException;

	/**
	 * 检查结果
	 * 
	 * @return
	 * @throws PayException
	 */
	String check() throws PayException;

	/**
	 * 状态
	 * 
	 * @return
	 */
	NameItem getState();

	/**
	 * 创建时间
	 * 
	 * @return
	 */
	Date getCreateTime();

	/**
	 * 设置回调链接
	 * 
	 * @param callback
	 */
	void setCallback(String callback);

	/**
	 * 获取回调链接
	 * 
	 * @return
	 */
	String getCallback();

	/***
	 * 设置用户
	 * 
	 * @param user
	 */
	void setUser(String user);

	/**
	 * 获取用户
	 * 
	 * @return
	 */
	String getUser();

}
