package cn.weforward.pay.weforward;

import org.springframework.stereotype.Component;

import cn.weforward.pay.PayException;
import cn.weforward.protocol.StatusCode;
import cn.weforward.protocol.client.execption.MicroserviceException;
import cn.weforward.protocol.support.CommonServiceCodes;

/**
 * 支付异常码
 * 
 * @author daibo
 *
 */
@Component
public class PayServiceCodes extends CommonServiceCodes {
	private final static StatusCode CODE_PAYEXCEPTION = new StatusCode(MicroserviceException.CUSTOM_CODE_START, "支付异常");

	static {
		append(CODE_PAYEXCEPTION);
	}

	/**
	 * 获取错误码
	 * 
	 * @param e
	 * @return
	 */
	public static int getCode(PayException e) {
		return CODE_PAYEXCEPTION.code;
	}

}
