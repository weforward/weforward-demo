package cn.weforward.order;

/**
 * 想要的商品
 * 
 * @author daibo
 *
 */
public interface WantGoods {

	/**
	 * 数量
	 * 
	 * @return 数量
	 */
	int getNum();

	/**
	 * 商品Id
	 * 
	 * @return 商品Id
	 */
	String getGoods();
}
