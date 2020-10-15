package cn.weforward.pay.impl;

import javax.annotation.Resource;

import cn.weforward.common.NameItem;
import cn.weforward.pay.Form;
import cn.weforward.pay.PayException;

/**
 * 支付渠道
 * 
 * @author daibo
 *
 */
public class PayChannel {
	@Resource
	protected String m_Name;
	@Resource
	protected String m_Provider;
	@Resource(type = String.class)
	protected Form m_Apply;
	@Resource(type = String.class)
	protected Form m_Request;
	@Resource(type = String.class)
	protected Form m_Result;

	protected PaymentExt m_Payment;

	protected PayChannel() {

	}

	public PayChannel(String name, String provider, PaymentExt payment) {
		m_Name = name;
		m_Provider = provider;
		m_Payment = payment;
	}

	public void bindPayment(PaymentExt payment) {
		m_Payment = payment;
	}

	public String getName() {
		return m_Name;
	}

	public String getProviderName() {
		return m_Provider;
	}

	public PayProvider getProvider() {
		return getPayment().getProvider(getProviderName());
	}

	public PaymentExt getPayment() {
		return m_Payment;
	}

	public Form getApply() {
		return m_Apply;
	}

	public void setApply(Form apply) {
		m_Apply = apply;
	}

	public Form getRequest() {
		return m_Request;
	}

	public void setRequest(Form request) {
		m_Request = request;
	}

	public Form getResult() {
		return m_Result;
	}

	public void setResult(Form result) {
		m_Result = result;
	}

	public Form request() throws PayException {
		if (null != m_Request) {
			return m_Request;
		}
		return getProvider().request(this);
	}

	public boolean result(Form form) throws PayException {
		return getProvider().result(this, form);
	}

	public void cancel() throws PayException {
		getProvider().cancel(this);
	}

	public NameItem check() {
		return getProvider().check(this);
	}

	public boolean onFinished(NameItem state, Form result, String reason) {
		if (getPayment().onFinished(state, this, reason)) {
			// 支付单状态变化，保存result
			if (null != result) {
				setResult(result);
			}
			return true;
		}
		return false;

	}

}
