package cn.weforward.product.weforward.view;

import cn.weforward.product.Trade;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 交易
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

	public static TradeView valueOf(Trade trade) {
		return null == trade ? null : new TradeView(trade);
	}

	@DocAttribute(description = "交易id")
	public String getId() {
		return m_Trade.getId().getOrdinal();
	}

	@DocAttribute(description = "交易金额")
	public int getAmount() {
		return m_Trade.getAmount();
	}

	@DocAttribute(description = "产品数量")
	public int getNum() {
		return m_Trade.getNum();
	}

	@DocAttribute(description = "关联产品")
	public ProductView getProduct() {
		return ProductView.valueOf(m_Trade.getProduct());
	}

	@DocAttribute(description = "交易状态")
	public int getState() {
		return m_Trade.getState().id;
	}

}
