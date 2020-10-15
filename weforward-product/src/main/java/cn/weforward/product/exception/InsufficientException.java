package cn.weforward.product.exception;

import cn.weforward.product.TradeException;

/**
 * 库存不足异常
 * 
 * @author daibo
 *
 */
public class InsufficientException extends TradeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientException() {
		super();
	}

	public InsufficientException(String message) {
		super(message);
	}

}
