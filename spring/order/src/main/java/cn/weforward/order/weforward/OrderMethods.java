package cn.weforward.order.weforward;

import java.util.List;

import javax.annotation.Resource;

import cn.weforward.common.ResultPage;
import cn.weforward.common.util.TransResultPage;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.framework.ApiException;
import cn.weforward.framework.ExceptionHandler;
import cn.weforward.framework.KeepServiceOrigin;
import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.framework.exception.ForwardException;
import cn.weforward.framework.util.ValidateUtil;
import cn.weforward.order.Order;
import cn.weforward.order.OrderException;
import cn.weforward.order.OrderService;
import cn.weforward.order.WantGoods;
import cn.weforward.order.kit.PayException;
import cn.weforward.order.weforward.view.LogView;
import cn.weforward.order.weforward.view.LogsParams;
import cn.weforward.order.weforward.view.MyordersParams;
import cn.weforward.order.weforward.view.OrderView;
import cn.weforward.order.weforward.view.SubmitParam;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocMethod;
import cn.weforward.protocol.doc.annotation.DocParameter;
import cn.weforward.protocol.doc.annotation.DocReturn;
import cn.weforward.protocol.support.datatype.FriendlyObject;

/**
 * 订单接口
 *
 */
@WeforwardMethods
public class OrderMethods implements ExceptionHandler {

	@Resource
	protected OrderService m_OrderService;
	@Resource
	protected OrderServiceCodes m_OrderServiceCodes;

	@WeforwardMethod
	@DocMethod(description = "创建订单", index = 1)
	@DocParameter(@DocAttribute(name = "user", type = String.class, description = "用户", example = "any"))
	public OrderView create(FriendlyObject params) throws ApiException {
		String user = params.getString("user");
		ValidateUtil.isEmpty(user, "用户不能为空");
		Order order = m_OrderService.createOrder(user);
		return OrderView.valueOf(order);
	}

	@WeforwardMethod
	@DocMethod(description = "提交订单", index = 2)
	public OrderView submit(SubmitParam params) throws ApiException, OrderException {
		String id = params.getId();
		ValidateUtil.isEmpty(id, "id不能为空");
		Order order = m_OrderService.getOrder(id);
		ForwardException.forwardToIfNeed(order);
		List<? extends WantGoods> goodslist = params.getGoodsList();
		order.submit(goodslist);
		return OrderView.valueOf(order);
	}

	@KeepServiceOrigin
	@WeforwardMethod
	@DocParameter({ @DocAttribute(name = "id", description = "订单id"),
			@DocAttribute(name = "channel", description = "支付渠道", example = "现金") })
	@DocMethod(description = "支付订单", index = 3)
	@DocReturn(description = "URI参数,如a=123&b=1024", type = String.class)
	public String pay(FriendlyObject params) throws ApiException, OrderException, PayException {
		String id = params.getString("id");
		String channel = params.getString("channel");
		ValidateUtil.isEmpty(id, "id不能为空");
		ValidateUtil.isEmpty(channel, "channel不能为空");
		Order order = m_OrderService.getOrder(id);
		ForwardException.forwardToIfNeed(order);
		return order.pay(channel).toLinkString();
	}

	@WeforwardMethod
	@DocParameter({ @DocAttribute(name = "id", description = "订单id"),
			@DocAttribute(name = "password", description = "确认密码", example = "666666") })
	@DocMethod(description = "确认支付", index = 4)
	public OrderView confirmPay(FriendlyObject params) throws PayException {
		String id = params.getString("id");
		String password = params.getString("password");
		Order order = m_OrderService.getOrder(id);
		order.confirmPay(password);
		return OrderView.valueOf(order);
	}

	@WeforwardMethod
	@DocParameter(@DocAttribute(name = "id", description = "订单id"))
	@DocMethod(description = "取消订单", index = 5)
	public OrderView cancel(FriendlyObject params) throws ApiException, OrderException {
		String id = params.getString("id");
		ValidateUtil.isEmpty(id, "id不能为空");
		Order order = m_OrderService.getOrder(id);
		ForwardException.forwardToIfNeed(order);
		order.cancel();
		return OrderView.valueOf(order);
	}

	@WeforwardMethod
	@DocParameter(@DocAttribute(name = "id", description = "订单id"))
	@DocMethod(description = "获取订单对象", index = 6)
	public OrderView get(FriendlyObject params) throws ApiException, OrderException {
		String id = params.getString("id");
		ValidateUtil.isEmpty(id, "id不能为空");
		Order order = m_OrderService.getOrder(id);
		ForwardException.forwardToIfNeed(order);
		return OrderView.valueOf(order);
	}

	@WeforwardMethod
	@DocMethod(description = "获取订单日志", index = 7)
	public ResultPage<LogView> logs(LogsParams params) throws ApiException, OrderException {
		String id = params.getId();
		ValidateUtil.isEmpty(id, "id不能为空");
		Order order = m_OrderService.getOrder(id);
		ForwardException.forwardToIfNeed(order);
		return new TransResultPage<LogView, BusinessLog>(order.getLogs()) {

			@Override
			protected LogView trans(BusinessLog src) {
				return LogView.valueOf(src);
			}
		};
	}

	@WeforwardMethod
	@DocMethod(description = "获取订单列表", index = 8)
	public ResultPage<OrderView> myorders(MyordersParams params) {
		String user = params.getUser();
		ResultPage<Order> rp = m_OrderService.myOrders(user, 0);
		return new TransResultPage<OrderView, Order>(rp) {

			@Override
			protected OrderView trans(Order src) {
				return OrderView.valueOf(src);
			}
		};
	}

	@KeepServiceOrigin
	@WeforwardMethod
	@DocMethod(description = "获取待支付的订单列表", index = 9)
	public ResultPage<OrderView> myWaitorders(MyordersParams params) {
		String user = params.getUser();
		ResultPage<Order> rp = m_OrderService.myOrders(user, OrderService.OPTION_WAITPAY);
		return new TransResultPage<OrderView, Order>(rp) {

			@Override
			protected OrderView trans(Order src) {
				return OrderView.valueOf(src);
			}
		};
	}

	@Override
	public Throwable exception(Throwable error) {
		if (error instanceof PayException) {
			return new ApiException(m_OrderServiceCodes.getCode(error), error.getMessage());
		}
		if (error instanceof OrderException) {
			return new ApiException(m_OrderServiceCodes.getCode(error), error.getMessage());
		}
		return error;
	}
}
