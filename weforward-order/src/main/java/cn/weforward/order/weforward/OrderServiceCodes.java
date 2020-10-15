package cn.weforward.order.weforward;

import org.springframework.stereotype.Component;

import cn.weforward.framework.doc.AbstractServiceCodes;
import cn.weforward.order.kit.PayException;
import cn.weforward.protocol.StatusCode;

/**
 * 订单异常码
 * 
 * @author daibo
 *
 */
@Component
public class OrderServiceCodes extends AbstractServiceCodes {
	private StatusCode m_PayException = genCode("支付异常");
	private StatusCode m_OrderException = genCode("订单异常");

	/**
	 * 获取错误码
	 * 
	 * @param e
	 * @return
	 */
	public int getCode(Throwable e) {
		if (e instanceof PayException) {
			return m_PayException.code;
		} else {
			return m_OrderException.code;
		}
	}

}
