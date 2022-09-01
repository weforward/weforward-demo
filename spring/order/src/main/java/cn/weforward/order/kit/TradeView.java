package cn.weforward.order.kit;

/**
 * 交易
 * 
 * @author daibo
 *
 */
public class TradeView {

	protected String m_Id;

	protected int m_Amount;

	protected int m_Num;

	protected ProductView m_Product;

	public TradeView() {
	}

	public String getId() {
		return m_Id;
	}

	public void setId(String id) {
		m_Id = id;
	}

	public int getAmount() {
		return m_Amount;
	}

	public void setAmount(int amount) {
		m_Amount = amount;
	}

	public int getNum() {
		return m_Num;
	}

	public void setNum(int num) {
		m_Num = num;
	}

	public ProductView getProduct() {
		return m_Product;
	}

	public void setProduct(ProductView product) {
		m_Product = product;
	}

}
