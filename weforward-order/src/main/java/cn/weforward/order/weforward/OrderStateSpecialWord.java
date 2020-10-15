package cn.weforward.order.weforward;

import org.springframework.stereotype.Component;

import cn.weforward.framework.doc.AbstractDocSpecialWord;
import cn.weforward.order.Order;

@Component
public class OrderStateSpecialWord extends AbstractDocSpecialWord {

	public OrderStateSpecialWord() {
		super("订单状态", null);
		setTabelHeader("订单状态id", "订单状态名称");
		addTableItem(Order.STATES);
	}

}
