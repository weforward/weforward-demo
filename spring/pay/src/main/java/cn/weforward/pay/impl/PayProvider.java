package cn.weforward.pay.impl;

import cn.weforward.common.NameItem;
import cn.weforward.common.Nameable;
import cn.weforward.pay.Form;
import cn.weforward.pay.PayException;

/**
 * 支付供应商
 * 
 * @author daibo
 *
 */
public interface PayProvider extends Nameable {
	/**
	 * 请求
	 * 
	 * @param channel
	 * @return
	 * @throws PayException
	 */
	Form request(PayChannel channel) throws PayException;

	/**
	 * 结果
	 * 
	 * @param channel
	 * @param result
	 * @return
	 * @throws PayException
	 */
	boolean result(PayChannel channel, Form result) throws PayException;

	/**
	 * 取消
	 * 
	 * @param channel
	 * @throws PayException
	 */
	void cancel(PayChannel channel) throws PayException;

	/**
	 * 检查
	 * 
	 * @param channel
	 * @return
	 */
	NameItem check(PayChannel channel);
}
