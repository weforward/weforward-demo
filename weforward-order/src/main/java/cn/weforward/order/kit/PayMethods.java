package cn.weforward.order.kit;

import cn.weforward.protocol.client.util.IdBean;

/**
 * 支付方法集
 *
 */
public interface PayMethods {
	/**
	 * 获取支付单
	 * 
	 * @param params
	 * @return
	 */
	PaymentView get(IdBean params);

	/**
	 * 创建
	 * 
	 * @param params
	 * @return
	 */
	PaymentView create(CreateParam params);

	/**
	 * 提交
	 * 
	 * @param params
	 * @return
	 * @throws PayException
	 */
	String submit(PayParam params) throws PayException;

	/**
	 * 结果通知
	 * 
	 * @param params
	 * @return
	 */
	PaymentView result(ResultParam params);

	/**
	 * 取消
	 * 
	 * @param params
	 * @return
	 */
	PaymentView cancel(IdBean params);

	/**
	 * 检查
	 * 
	 * @param params
	 * @return
	 */
	PaymentView check(IdBean params);

	/**
	 * 收到现金
	 * 
	 * @param params
	 * @return
	 */
	PaymentView cashReceived(IdBean params);
}
