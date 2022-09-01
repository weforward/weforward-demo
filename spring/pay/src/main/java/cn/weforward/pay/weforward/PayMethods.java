package cn.weforward.pay.weforward;

import javax.annotation.Resource;

import cn.weforward.framework.ApiException;
import cn.weforward.framework.ExceptionHandler;
import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.framework.exception.ForwardException;
import cn.weforward.framework.util.ValidateUtil;
import cn.weforward.pay.Form;
import cn.weforward.pay.PayException;
import cn.weforward.pay.PayService;
import cn.weforward.pay.Payment;
import cn.weforward.pay.weforward.view.CreateParam;
import cn.weforward.pay.weforward.view.PayParam;
import cn.weforward.pay.weforward.view.PaymentView;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocMethod;
import cn.weforward.protocol.doc.annotation.DocParameter;
import cn.weforward.protocol.doc.annotation.DocReturn;
import cn.weforward.protocol.support.datatype.FriendlyObject;

/**
 * 支付方法集
 *
 */
@WeforwardMethods
public class PayMethods implements ExceptionHandler {
	@Resource
	private PayService m_PayService;

	@WeforwardMethod
	@DocMethod(description = "创建支付单", index = 1000)
	public PaymentView create(CreateParam params) throws ApiException {
		ValidateUtil.ltOrEqZero(params.getAmount(), "金额必须大于0");
		ValidateUtil.ltOrEqZero(params.getTimeout(), "超时时间必须大于0");
		Payment payment = m_PayService.createPayment(params.getAmount(), params.getTimeout());
		payment.setCallback(params.getCallback());
		payment.setUser(params.getUser());
		return PaymentView.valueOf(payment);
	}

	@WeforwardMethod
	@DocMethod(description = "提交支付单", index = 1001)
	@DocReturn(description = "URI参数,如a=123&b=1024", type = String.class)
	public String submit(PayParam params) throws ApiException, PayException {
		String id = params.getId();
		ValidateUtil.isEmpty(id, "id不能为空");
		Payment payment = m_PayService.getPayment(id);
		ForwardException.forwardToIfNeed(payment);
		String channel = params.getChannel();
		return payment.submit(channel).toLinkString();
	}

//	@WeforwardMethod
//	@DocMethod(description = "支付单结果通知", index = 1002)
//	public PaymentView result(ResultParam params) throws ApiException, PayException {
//		String id = params.getId();
//		ValidateUtil.isEmpty(id, "id不能为空");
//		Payment payment = m_PayService.getPayment(id);
//		ForwardException.forwardToIfNeed(payment);
//		Form form = new Form();
//		List<FormItem> items = params.getForm();
//		if (null != items) {
//			for (FormItem item : items) {
//				form.put(item.getName(), item.getValue());
//			}
//		}
//		payment.notifyPay(form);
//		return PaymentView.valueOf(payment);
//	}

	@WeforwardMethod
	@DocParameter(@DocAttribute(name = "id", description = "支付单id"))
	@DocMethod(description = "取消支付单", index = 1003)
	public PaymentView cancel(FriendlyObject params) throws ApiException, PayException {
		String id = params.getString("id");
		ValidateUtil.isEmpty(id, "id不能为空");
		Payment payment = m_PayService.getPayment(id);
		ForwardException.forwardToIfNeed(payment);
		payment.cancel();
		return PaymentView.valueOf(payment);
	}

	@WeforwardMethod
	@DocParameter(@DocAttribute(name = "id", description = "支付单id"))
	@DocMethod(description = "检查支付单状态", index = 1004)
	public PaymentView check(FriendlyObject params) throws ApiException, PayException {
		String id = params.getString("id");
		ValidateUtil.isEmpty(id, "id不能为空");
		Payment payment = m_PayService.getPayment(id);
		ForwardException.forwardToIfNeed(payment);
		payment.check();
		return PaymentView.valueOf(payment);
	}

	@WeforwardMethod
	@DocParameter(@DocAttribute(name = "id", description = "支付单id"))
	@DocMethod(description = "获取支付单对象", index = 1005)
	public PaymentView get(FriendlyObject params) throws ApiException {
		String id = params.getString("id");
		ValidateUtil.isEmpty(id, "id不能为空");
		Payment payment = m_PayService.getPayment(id);
		ForwardException.forwardToIfNeed(payment);
		return PaymentView.valueOf(payment);
	}

	@WeforwardMethod
	@DocParameter(@DocAttribute(name = "id", description = "支付单id"))
	@DocMethod(description = "收到现金", index = 1006)
	public PaymentView cashReceived(FriendlyObject params) throws ApiException, PayException {
		String id = params.getString("id");
		ValidateUtil.isEmpty(id, "id不能为空");
		Payment payment = m_PayService.getPayment(id);
		ForwardException.forwardToIfNeed(payment);
		Form form = new Form();
		form.put("state", String.valueOf(Payment.STATE_SUCCESS.id));
		payment.notifyPay(form);
		return PaymentView.valueOf(payment);
	}

	@Override
	public Throwable exception(Throwable error) {
		if (error instanceof PayException) {
			return new ApiException(PayServiceCodes.getCode((PayException) error), error.getMessage());
		}
		return error;
	}
}
