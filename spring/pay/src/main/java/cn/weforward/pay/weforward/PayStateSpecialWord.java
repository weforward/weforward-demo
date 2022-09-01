package cn.weforward.pay.weforward;

import org.springframework.stereotype.Component;

import cn.weforward.framework.doc.AbstractDocSpecialWord;
import cn.weforward.pay.Payment;

@Component
public class PayStateSpecialWord extends AbstractDocSpecialWord {

	public PayStateSpecialWord() {
		super("支付状态", null);
		setTabelHeader("支付状态id", "支付状态名称");
		addTableItem(Payment.STATES);
	}

}
