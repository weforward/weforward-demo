package cn.weforward.pay.impl.provider;

import cn.weforward.common.NameItem;
import cn.weforward.common.util.NumberUtil;
import cn.weforward.pay.Form;
import cn.weforward.pay.Payment;
import cn.weforward.pay.di.PayDi;
import cn.weforward.pay.impl.PayChannel;
import cn.weforward.pay.impl.PayProvider;

/**
 * 现金渠道
 * 
 * @author daibo
 *
 */
public class CashPayProvider implements PayProvider {

	public CashPayProvider(PayDi di) {
		di.register(this);
	}

	@Override
	public String getName() {
		return "现金";
	}

	@Override
	public Form request(PayChannel channel) {
		Form apply = new Form();
		channel.setApply(apply);
		Form requset = new Form();
		requset.put("state", String.valueOf(Payment.STATE_SUCCESS.id));
		channel.setRequest(requset);
		return requset;
	}

	@Override
	public boolean result(PayChannel channel, Form result) {
		Form apply = channel.getApply();
		if (null == apply) {
			return false;
		}
		String state = result.get("state");
		if (NumberUtil.toInt(state, 0) == Payment.STATE_SUCCESS.id) {
			channel.onFinished(Payment.STATE_SUCCESS, result, null);
			return true;
		}
		return false;
	}

	@Override
	public void cancel(PayChannel channel) {
		Form apply = channel.getApply();
		if (null == apply) {
			return;
		}
		Form result = new Form();
		result.put("state", String.valueOf(Payment.STATE_CANCEL.id));
		channel.setResult(result);
	}

	@Override
	public NameItem check(PayChannel channel) {
		return channel.getPayment().getState();
	}

}
