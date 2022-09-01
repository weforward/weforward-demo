package cn.weforward.pay.weforward.view;

import java.util.List;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 结果参数
 * 
 * @author daibo
 *
 */
@DocObject(description = "支付参数")
public class ResultParam {

	protected String m_Id;

	protected List<FormItem> m_Form;

	@DocAttribute(description = "唯一id", necessary = true)
	public String getId() {
		return m_Id;
	}

	public void setId(String id) {
		m_Id = id;
	}

	@DocAttribute(description = "表单对象")
	public List<FormItem> getForm() {
		return m_Form;
	}

	public void setForm(List<FormItem> form) {
		m_Form = form;
	}
}
