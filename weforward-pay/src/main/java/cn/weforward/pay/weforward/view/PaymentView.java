package cn.weforward.pay.weforward.view;

import cn.weforward.pay.Payment;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 支付单
 * 
 * @author daibo
 *
 */
@DocObject(description = "支付单")
public class PaymentView {

	protected Payment m_Payment;

	public PaymentView(Payment payment) {
		m_Payment = payment;
	}

	public static PaymentView valueOf(Payment payment) {
		return null == payment ? null : new PaymentView(payment);
	}

	@DocAttribute(description = "唯一id")
	public String getId() {
		return m_Payment.getId();
	}

	@DocAttribute(description = "金额，单位分")
	public int getAmount() {
		return m_Payment.getAmount();
	}

	@DocAttribute(description = "剩余时间，单位秒 ")
	public int getRemainingTime() {
		long t = (m_Payment.getCreateTime().getTime() + (m_Payment.getTimeout() * 1000));
		long r = (t - System.currentTimeMillis());
		return r < 0 ? 0 : (int) (r / 1000);
	}

	@DocAttribute(description = "状态 0:初始 1:已提交 2:已取消 3:成功 ")
	public int getState() {
		return m_Payment.getState().id;
	}

	@DocAttribute(description = "状态描述")
	public String getStateDesc() {
		return m_Payment.getState().name;
	}
}
