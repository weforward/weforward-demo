package cn.weforward.order.kit;

/**
 * 产品视图
 * 
 * @author daibo
 *
 */
public class ProductView {

	protected String m_Id;

	protected String m_Name;

	protected int m_Price;

	public ProductView() {
	}

	public String getId() {
		return m_Id;
	}

	public void setId(String id) {
		m_Id = id;
	}

	public String getName() {
		return m_Name;
	}

	public void setName(String name) {
		m_Name = name;
	}

	public int getPrice() {
		return m_Price;
	}

	public void setPrice(int price) {
		m_Price = price;
	}
}
