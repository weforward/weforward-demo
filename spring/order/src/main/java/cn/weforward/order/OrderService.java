package cn.weforward.order;

import cn.weforward.common.ResultPage;

/**
 * 订单服务
 * 
 * @author daibo
 *
 */
public interface OrderService {
	/** 选项-待付款 */
	int OPTION_WAITPAY = 1;

	/**
	 * 创建订单
	 * 
	 * @param user
	 * @return
	 */
	Order createOrder(String user);

	/**
	 * 我的订单
	 * 
	 * @param user
	 * @param options
	 * @return
	 */
	ResultPage<Order> myOrders(String user, int options);

	/**
	 * 获取订单
	 * 
	 * @param id
	 * @return
	 */
	Order getOrder(String id);

	/**
	 * 根据支付单查找订单
	 * 
	 * @param payment
	 * @return
	 */
	Order findOrder(String payment);
}
