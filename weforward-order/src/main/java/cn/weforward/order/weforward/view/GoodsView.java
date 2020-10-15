package cn.weforward.order.weforward.view;

import cn.weforward.order.Goods;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 商品视图
 * 
 * @author daibo
 *
 */
@DocObject(description = "商品视图")
public class GoodsView {

	protected Goods m_Goods;

	public GoodsView(Goods goods) {
		m_Goods = goods;
	}

	public static GoodsView valueOf(Goods src) {
		return null == src ? null : new GoodsView(src);
	}

	@DocAttribute(description = "产品id")
	public String getId() {
		return m_Goods.getId();
	}

	@DocAttribute(description = "产品价格，单位分")
	public int getPrice() {
		return m_Goods.getPrice();
	}

	@DocAttribute(description = "产品名称")
	public String getName() {
		return m_Goods.getName();
	}

}
