package cn.weforward.pay.weforward.view;

import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

@DocObject(description = "创建支付单参数")
public class CreateParam {

	protected int m_Timeout;

	protected int m_Amount;

	protected String m_Callback;

	protected String m_User;

	@DocAttribute(description = "超时时间，单位秒", necessary = true, example = "600")
	public int getTimeout() {
		return m_Timeout;
	}

	public void setTimeout(int timeout) {
		m_Timeout = timeout;
	}

	@DocAttribute(description = "支付金额，单位分", necessary = true, example = "10")
	public int getAmount() {
		return m_Amount;
	}

	public void setAmount(int amount) {
		m_Amount = amount;
	}

	@DocAttribute(description = "回调uri", example = "event://order")
	public String getCallback() {
		return m_Callback;
	}

	public void setCallback(String callback) {
		m_Callback = callback;
	}

	@DocAttribute(description = "用户", example = "admin")
	public String getUser() {
		return m_User;
	}

	public void setUser(String user) {
		m_User = user;
	}
}
