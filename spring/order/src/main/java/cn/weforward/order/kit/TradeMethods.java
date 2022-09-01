package cn.weforward.order.kit;

import cn.weforward.protocol.client.util.IdBean;

/**
 * 交易方法集
 * 
 * @author daibo
 *
 */
public interface TradeMethods {
	/**
	 * 获取交易
	 * 
	 * @param id
	 * @return
	 */
	TradeView get(IdBean id);

	/**
	 * 创建交易
	 * 
	 * @param params
	 * @return
	 * @throws TradeException
	 */
	TradeView create(TradeParam params) throws TradeException;

	/**
	 * 锁定交易
	 * 
	 * @param id
	 * @return
	 * @throws TradeException
	 */
	TradeView lock(IdBean id) throws TradeException;

	/**
	 * 解锁交易
	 * 
	 * @param id
	 * @return
	 * @throws TradeException
	 */
	TradeView unlock(IdBean id) throws TradeException;

	/**
	 * 确认交易
	 * 
	 * @param id
	 * @return
	 * @throws TradeException
	 */
	TradeView confirm(IdBean id) throws TradeException;

}
