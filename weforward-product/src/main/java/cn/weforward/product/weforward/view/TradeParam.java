package cn.weforward.product.weforward.view;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 交易参数
 * 
 * @author daibo
 *
 */
@DocObject(description = "交易参数")
public class TradeParam {

	protected String m_ProductId;

	protected int m_Num;

	@DocAttribute(description = "产品id")
	public String getProductId() {
		return m_ProductId;
	}

	public void setProductId(String id) {
		m_ProductId = id;
	}

	@DocAttribute(description = "数量")
	public int getNum() {
		return m_Num;
	}

	public void setNum(int num) {
		m_Num = num;
	}
}
