package cn.weforward.order.weforward.view;

import cn.weforward.order.WantGoods;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 商品参数
 * 
 * @author daibo
 *
 */
@DocObject(description = "商品参数")
public class GoodsParam implements WantGoods {

	protected int m_Num;

	protected String m_Goods;

	public void setNum(int num) {
		m_Num = num;
	}

	@DocAttribute(description = "商品数量", example = "1")
	public int getNum() {
		return m_Num;
	}

	public void setGoods(String goods) {
		m_Goods = goods;
	}

	@DocAttribute(description = "商品id", example = "xxx")
	public String getGoods() {
		return m_Goods;
	}
}
