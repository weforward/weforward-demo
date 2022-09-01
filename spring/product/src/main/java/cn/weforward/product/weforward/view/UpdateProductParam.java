package cn.weforward.product.weforward.view;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

@DocObject(description = "更新产品参数")
public class UpdateProductParam extends ProductParam {

	protected String m_Id;

	public void setId(String id) {
		m_Id = id;
	}

	@DocAttribute(necessary = true, description = "产品id", example = "123")
	public String getId() {
		return m_Id;
	}

}
