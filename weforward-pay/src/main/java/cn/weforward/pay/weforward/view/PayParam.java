package cn.weforward.pay.weforward.view;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 支付参数
 * 
 * @author daibo
 *
 */
@DocObject(description = "支付参数")
public class PayParam {

	protected String m_Id;

	protected String m_Channel;

	@DocAttribute(description = "唯一id")
	public String getId() {
		return m_Id;
	}

	public void setId(String id) {
		m_Id = id;
	}

	@DocAttribute(description = "渠道")
	public String getChannel() {
		return m_Channel;
	}

	public void setChannel(String channel) {
		m_Channel = channel;
	}
}
