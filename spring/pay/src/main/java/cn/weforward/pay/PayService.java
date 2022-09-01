package cn.weforward.pay;

/**
 * 支付服务
 * 
 * @author daibo
 *
 */
public interface PayService {
	/**
	 * 创建支付单
	 * 
	 * @param amount  金额
	 * @param timeout 超时时间(单位秒)
	 * @return 支付单
	 */
	Payment createPayment(int amount, int timeout);

	/**
	 * 获取支付单
	 * 
	 * @param id id
	 * @return 支付单
	 */
	Payment getPayment(String id);
}
