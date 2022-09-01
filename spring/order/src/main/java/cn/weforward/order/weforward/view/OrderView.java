package cn.weforward.order.weforward.view;

import java.util.List;

import cn.weforward.common.util.TransList;
import cn.weforward.order.Order;
import cn.weforward.order.Trade;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 订单视图
 * 
 * @author daibo
 *
 */
@DocObject(description = "订单视图")
public class OrderView {

	protected Order m_Order;

	public OrderView(Order order) {
		m_Order = order;
	}

	public static OrderView valueOf(Order order) {
		return null == order ? null : new OrderView(order);
	}

	@DocAttribute(description = "订单唯一id")
	public String getId() {
		return m_Order.getId().getOrdinal();
	}

	@DocAttribute(description = "订单金额，单位分")
	public int getAmount() {
		return m_Order.getAmount();
	}

	@DocAttribute(description = "支付单号")
	public String getPayno() {
		return m_Order.getPayno();
	}

	@DocAttribute(description = "状态")
	public int getState() {
		return m_Order.getState().id;
	}

	@DocAttribute(description = "状态描述")
	public String getStateDesc() {
		return m_Order.getState().name;
	}

	@DocAttribute(description = "交易")
	public List<TradeView> getTrades() {
		return new TransList<TradeView, Trade>(m_Order.getTrades()) {

			@Override
			protected TradeView trans(Trade src) {
				return TradeView.valueOf(src);
			}
		};
	}
}
