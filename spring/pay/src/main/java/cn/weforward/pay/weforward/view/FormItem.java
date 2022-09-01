package cn.weforward.pay.weforward.view;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 表单项
 * 
 * @author daibo
 *
 */
@DocObject(description = "表单项")
public class FormItem {

	protected String m_Name;

	protected String m_Value;

	public FormItem() {

	}

	public void setName(String name) {
		m_Name = name;
	}

	@DocAttribute(description = "名称")
	public String getName() {
		return m_Name;
	}

	public void setValue(String value) {
		m_Value = value;
	}

	@DocAttribute(description = "值")
	public String getValue() {
		return m_Value;
	}
}
