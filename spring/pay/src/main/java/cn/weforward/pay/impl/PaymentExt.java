package cn.weforward.pay.impl;

import cn.weforward.common.NameItem;
import cn.weforward.pay.Payment;

/**
 * 支付单扩展对象
 * 
 * @author daibo
 *
 */
public interface PaymentExt extends Payment {
	/**
	 * 获取供应商
	 * 
	 * @param providerName
	 * @return
	 */
	PayProvider getProvider(String providerName);

	/**
	 * 结果事件
	 * 
	 * @param state      状态
	 * @param payChannel 支付渠道
	 * @param reason     原因
	 * @return
	 */
	boolean onFinished(NameItem state, PayChannel payChannel, String reason);

}
