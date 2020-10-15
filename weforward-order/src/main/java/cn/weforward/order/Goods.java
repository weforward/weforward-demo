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
	 * @return
	 */
	String getId();

	/**
	 * 商品价格
	 * 
	 * @return
	 */
	int getPrice();

	/**
	 * 商品名称
	 * 
	 * @return
	 */
	String getName();

}
