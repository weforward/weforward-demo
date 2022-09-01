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
	 * @return 交易id
	 */
	String getId();

	/**
	 * 交易金额
	 * 
	 * @return 交易金额
	 */
	int getAmount();

	/**
	 * 商品数量
	 * 
	 * @return 商品数量
	 */
	int getNum();

	/**
	 * 商品
	 * 
	 * @return 商品
	 */
	Goods getGoods();

}
