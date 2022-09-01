package cn.weforward.order;

/**
 * 商品
 * 
 * @author daibo
 *
 */
public interface Goods {
	/**
	 * 商品id
	 * 
	 * @return 商品id
	 */
	String getId();

	/**
	 * 商品价格
	 * 
	 * @return  商品价格
	 */
	int getPrice();

	/**
	 * 商品名称
	 * 
	 * @return 商品名称
	 */
	String getName();

}
