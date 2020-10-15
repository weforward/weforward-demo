package cn.weforward.pay.impl;

import cn.weforward.data.persister.PersisterFactory;
import cn.weforward.pay.PayService;
import cn.weforward.pay.Payment;

/**
 * 支付服务实现
 * 
 * @author daibo
 *
 */
public class PayServiceImpl extends PayDiImpl implements PayService {

	public PayServiceImpl(PersisterFactory factory) {
		super(factory);
	}

	@Override
	public Payment createPayment(int amount, int timeout) {
		return new SimplePayment(this, amount, timeout);
	}

	@Override
	public Payment getPayment(String id) {
		return m_PsPayment.get(id);
	}

}
