package cn.weforward.order.kit;

/**
 * 创建参数
 * 
 * @author daibo
 *
 */
public class CreateParam {

	protected int m_Timeout;

	protected int m_Amount;

	protected String m_Callback;

	protected String m_User;

	/** 超时时间，单位秒 */
	public int getTimeout() {
		return m_Timeout;
	}

	public void setTimeout(int timeout) {
		m_Timeout = timeout;
	}

	/** 支付金额，单位分 */
	public int getAmount() {
		return m_Amount;
	}

	public void setAmount(int amount) {
		m_Amount = amount;
	}

	/** 回调uri,如event://order */
	public String getCallback() {
		return m_Callback;
	}

	public void setCallback(String callback) {
		m_Callback = callback;
	}

	/** 用户 */
	public String getUser() {
		return m_User;
	}

	public void setUser(String user) {
		m_User = user;
	}
}
