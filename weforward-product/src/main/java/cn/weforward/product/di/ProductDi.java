package cn.weforward.product.di;

import cn.weforward.data.UniteId;
import cn.weforward.data.counter.Counter;
import cn.weforward.data.persister.BusinessDi;
import cn.weforward.product.Picture;
import cn.weforward.product.Product;

/**
 * 产品依赖di
 * 
 * @author daibo
 *
 */
public interface ProductDi extends BusinessDi {
	/**
	 * 获取产品计数器
	 * 
	 * @return
	 */
	Counter getProductCounter();

	/**
	 * 获取产品
	 * 
	 * @param id
	 * @return
	 */
	Product getProduct(UniteId id);

	/**
	 * 获取图片
	 * 
	 * @param src
	 * @return
	 */
	Picture getPicture(String src);

}
