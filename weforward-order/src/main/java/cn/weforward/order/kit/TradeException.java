package cn.weforward.order.kit;

/**
 * 交易异常
 * 
 * @author daibo
 *
 */
public class TradeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TradeException() {
		super();
	}

	public TradeException(String message) {
		super(message);
	}

	public TradeException(String message, Throwable e) {
		super(message, e);
	}

}
