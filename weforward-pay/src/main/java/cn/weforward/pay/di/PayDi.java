package cn.weforward.pay.di;

import cn.weforward.data.persister.BusinessDi;
import cn.weforward.pay.impl.PayProvider;
import cn.weforward.protocol.client.EventInvoker;

/**
 * 支付依赖di
 * 
 * @author daibo
 *
 */
public interface PayDi extends BusinessDi {
	/**
	 * 查找供应商
	 * 
	 * @param name
	 * @return
	 */
	PayProvider findProvider(String name);

	/**
	 * 获取供应商
	 * 
	 * @param providerName
	 * @return
	 */
	PayProvider getProvider(String providerName);

	/**
	 * 注册支付供应商
	 * 
	 * @param provider
	 */
	void register(PayProvider provider);

	/**
	 * 注销支付供应商
	 * 
	 * @param provider
	 */
	void unRegister(PayProvider provider);

	/**
	 * 事件调用器
	 * 
	 * @return
	 */
	EventInvoker getEventInvoker();

}
