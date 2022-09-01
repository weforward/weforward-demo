package cn.weforward.order.weforward.view;

import cn.weforward.order.Trade;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 交易视图
 * 
 * @author daibo
 *
 */
@DocObject(description = "交易视图")
public class TradeView {

	protected Trade m_Trade;

	public TradeView(Trade trade) {
		m_Trade = trade;
	}

	public static TradeView valueOf(Trade src) {
		return null == src ? null : new TradeView(src);
	}

	@DocAttribute(description = "交易id")
	public String getId() {
		return m_Trade.getId();
	}

	@DocAttribute(description = "交易金额，单位分")
	public int getAmount() {
		return m_Trade.getAmount();
	}

	@DocAttribute(description = "商品数量")
	public int getNum() {
		return m_Trade.getNum();
	}

	@DocAttribute(description = "商品")
	public GoodsView getGoods() {
		return GoodsView.valueOf(m_Trade.getGoods());
	}

}
