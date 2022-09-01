package cn.weforward.order;

/**
 * 订单异常
 * 
 * @author daibo
 *
 */
public class OrderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderException() {
		super();
	}

	public OrderException(String msg) {
		super(msg);
	}

	public OrderException(String msg, Throwable e) {
		super(msg, e);
	}

}
