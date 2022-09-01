package cn.weforward.order.impl;

import cn.weforward.common.ResultPage;
import cn.weforward.common.util.ResultPageHelper;
import cn.weforward.data.log.BusinessLoggerFactory;
import cn.weforward.data.persister.PersisterFactory;
import cn.weforward.data.persister.ext.ConditionUtil;
import cn.weforward.order.Order;
import cn.weforward.order.OrderService;

/**
 * 订单服务实现
 * 
 * @author daibo
 *
 */
public class OrderServiceImpl extends OrderDiImpl implements OrderService {

	public OrderServiceImpl(PersisterFactory persisterFactory, BusinessLoggerFactory loggerFactory) {
		super(persisterFactory, loggerFactory);
	}

	@Override
	public Order createOrder(String user) {
		return new SimpleOrder(this, user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultPage<Order> myOrders(String user, int options) {
		ResultPage<? extends Order> rp;
		if ((options & OPTION_WAITPAY) == OPTION_WAITPAY) {
			rp = m_PsOrder.search(ConditionUtil.and(ConditionUtil.eq(SimpleOrder.USER_FIELD, user),
					ConditionUtil.eq(ConditionUtil.field("m_Vo", "state"), Order.STATE_WAITPAY.id)));
		} else {
			rp = m_PsOrder.search(ConditionUtil.eq(SimpleOrder.USER_FIELD, user));
		}
		return (ResultPage<Order>) rp;
	}

	@Override
	public Order getOrder(String id) {
		return m_PsOrder.get(id);
	}

	@Override
	public Order findOrder(String payment) {
		ResultPage<? extends Order> rp = m_PsOrder.search(ConditionUtil.eq(SimpleOrder.PAYMENT_FIELD, payment));
		for (Order order : ResultPageHelper.toForeach(rp)) {
			if (null != order) {
				return order;
			}
		}
		return null;
	}

}
