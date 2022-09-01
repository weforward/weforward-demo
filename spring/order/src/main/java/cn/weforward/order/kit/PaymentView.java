package cn.weforward.order.kit;

import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 支付单
 * 
 * @author daibo
 *
 */
@DocObject(description = "支付单")
public class PaymentView {

	protected String m_Id;

	protected int m_Amount;

	protected int m_RemainingTime;

	protected int m_State;

	protected String m_StateDesc;

	public PaymentView() {
	}

	public String getId() {
		return m_Id;
	}

	public void setId(String id) {
		m_Id = id;
	}

	public int getAmount() {
		return m_Amount;
	}

	public void setAmount(int amount) {
		m_Amount = amount;
	}

	public int getRemainingTime() {
		return m_RemainingTime;
	}

	public void setRemainingTime(int time) {
		m_RemainingTime = time;
	}

	public int getState() {
		return m_State;
	}

	public void setState(int state) {
		m_State = state;
	}

	public String getStateDesc() {
		return m_StateDesc;
	}

	public void setStateDesc(String desc) {
		m_StateDesc = desc;
	}
}
