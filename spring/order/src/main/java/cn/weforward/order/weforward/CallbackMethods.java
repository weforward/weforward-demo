package cn.weforward.order.weforward;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.framework.exception.ForwardException;
import cn.weforward.order.Order;
import cn.weforward.order.OrderService;
import cn.weforward.protocol.support.datatype.FriendlyObject;

@WeforwardMethods(root = true)
public class CallbackMethods {

	static Logger _Logger = LoggerFactory.getLogger(CallbackMethods.class);
	@Resource
	protected OrderService m_OrderService;

	@WeforwardMethod
	public void onPaySuccess(FriendlyObject params) {
		String id = params.getString("id");
		Order order = m_OrderService.findOrder(id);
		ForwardException.forwardToIfNeed(order);
		order.onPaySuccess();
	}

	@WeforwardMethod
	public void onPayFail(FriendlyObject params) {
		String id = params.getString("id");
		Order order = m_OrderService.findOrder(id);
		ForwardException.forwardToIfNeed(order);
		order.onPayFail();
	}
}
