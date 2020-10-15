package cn.weforward.product.weforward;

import org.springframework.stereotype.Component;

import cn.weforward.product.TradeException;
import cn.weforward.product.exception.InsufficientException;
import cn.weforward.protocol.StatusCode;
import cn.weforward.protocol.client.execption.MicroserviceException;
import cn.weforward.protocol.support.CommonServiceCodes;

/**
 * 产品异常码
 * 
 * @author daibo
 *
 */
@Component
public class ProductServiceCodes extends CommonServiceCodes {

	private final static StatusCode CODE_TRADEEXCEPTION = new StatusCode(MicroserviceException.CUSTOM_CODE_START,
			"交易异常");
	private final static StatusCode CODE_INSUFFICIENTEXCEPTION = new StatusCode(
			MicroserviceException.CUSTOM_CODE_START + 1, "库存不足");
	static {
		append(CODE_TRADEEXCEPTION, CODE_INSUFFICIENTEXCEPTION);
	}

	/**
	 * 获取错误码
	 * 
	 * @param e
	 * @return
	 */
	public static int getCode(TradeException e) {
		if (e instanceof InsufficientException) {
			return CODE_INSUFFICIENTEXCEPTION.code;
		}
		return CODE_TRADEEXCEPTION.code;
	}

}
