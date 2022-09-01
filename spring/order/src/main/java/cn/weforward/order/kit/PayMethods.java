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
	 */
	PaymentView get(IdBean params);

	/**
	 * 创建
	 *
	 */
	PaymentView create(CreateParam params);

	/**
	 * 提交
	 *
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
	 */
	PaymentView cancel(IdBean params);

	/**
	 * 检查
	 *
	 */
	PaymentView check(IdBean params);

	/**
	 * 收到现金
	 *
	 */
	PaymentView cashReceived(IdBean params);
}
