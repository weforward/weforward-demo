package cn.weforward.order.weforward.view;

import java.util.List;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 提交参数
 * 
 * @author daibo
 *
 */
@DocObject(description = "提交参数")
public class SubmitParam {

	protected String m_Id;

	protected List<GoodsParam> m_GoodsList;

	public void setId(String id) {
		m_Id = id;
	}

	@DocAttribute(description = "订单id", example = "xxx")
	public String getId() {
		return m_Id;
	}

	public void setGoodsList(List<GoodsParam> list) {
		m_GoodsList = list;
	}

	@DocAttribute(description = "商品列表", example = "{ \"goods\":\"xxx\" , \"num\":1}")
	public List<GoodsParam> getGoodsList() {
		return m_GoodsList;
	}
}
