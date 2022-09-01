package cn.weforward.order.weforward.view;

import cn.weforward.framework.doc.DocPageParams;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 
 * @author daibo
 *
 */
@DocObject(description = "我的订单搜索参数")
public class MyordersParams extends DocPageParams {

	protected String m_User;

	public void setUser(String user) {
		m_User = user;
	}

	@DocAttribute(name = "user", description = "用户", example = "any")
	public String getUser() {
		return m_User;
	}
}
