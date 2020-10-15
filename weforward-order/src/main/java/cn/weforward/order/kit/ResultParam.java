package cn.weforward.order.kit;

import java.util.Enumeration;

import cn.weforward.order.Form;
import cn.weforward.protocol.datatype.DtBase;
import cn.weforward.protocol.datatype.DtObject;
import cn.weforward.protocol.datatype.DtString;

/**
 * 结果参数
 * 
 * @author daibo
 *
 */
public class ResultParam {

	protected String m_Id;

	protected Form m_Form;

	public String getId() {
		return m_Id;
	}

	public void setId(String id) {
		m_Id = id;
	}

	public Form getForm() {
		return m_Form;
	}

	public void setForm(DtObject object) {
		Enumeration<String> names = object.getAttributeNames();
		Form form = new Form();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			DtBase base = object.getAttribute(key);
			if (base instanceof DtString) {
				form.put(key, ((DtString) base).value());
			}
		}
		m_Form = form;
	}
}
