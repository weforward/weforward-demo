package cn.weforward.order.kit;

/**
 * 支付异常
 * 
 * @author daibo
 *
 */
public class PayException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PayException() {
		super();
	}

	public PayException(String msg) {
		super(msg);
	}

	public PayException(String msg, Throwable e) {
		super(msg, e);
	}

}
