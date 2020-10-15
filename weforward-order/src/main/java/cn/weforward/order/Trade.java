package cn.weforward.order;

/**
 * 交易
 * 
 * @author daibo
 *
 */
public interface Trade {
	/**
	 * 交易id
	 * 
	 * @return
	 */
	String getId();

	/**
	 * 交易金额
	 * 
	 * @return
	 */
	int getAmount();

	/**
	 * 商品数量
	 * 
	 * @return
	 */
	int getNum();

	/**
	 * 商品
	 * 
	 * @return
	 */
	Goods getGoods();

}
