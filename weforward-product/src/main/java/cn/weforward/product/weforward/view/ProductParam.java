package cn.weforward.product.weforward.view;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

@DocObject(description = "产品参数")
public class ProductParam {

	protected String m_Name;

	protected String m_Desc;

	protected int m_Price;

	protected int m_Sum;

	public void setName(String name) {
		m_Name = name;
	}

	@DocAttribute(necessary = true, description = "产品名称", example = "火龙果")
	public String getName() {
		return m_Name;
	}

	public void setDesc(String desc) {
		m_Desc = desc;
	}

	@DocAttribute(necessary = true, description = "产品描述", example = "红肉火龙果")
	public String getDesc() {
		return m_Desc;
	}

	public void setPrice(int price) {
		m_Price = price;
	}

	@DocAttribute(necessary = true, description = "产品价格，单位分", example = "100")
	public int getPrice() {
		return m_Price;
	}

	public void setSum(int num) {
		m_Sum = num;
	}

	@DocAttribute(necessary = true, description = "产品库存", example = "9999")
	public int getSum() {
		return m_Sum;
	}
}
