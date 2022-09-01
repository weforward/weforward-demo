package cn.weforward.product;

import cn.weforward.common.ResultPage;

/**
 * 产品服务
 * 
 * @author daibo
 *
 */
public interface ProductService {
	/** 选项-无 */
	int OPTION_NONE = 0;
	/** 选项-上架中 */
	int OPTION_UP = 1;
	/** 选项-下架中 */
	int OPTION_DOWN = 2;

	/**
	 * 创建产品
	 * 
	 * @param merchant 商家
	 * @param name     名称
	 * @param desc     描述
	 * @param price    价格
	 * @param sum      库存
	 * @return
	 */
	Product createProduct(String merchant, String name, String desc, int price, int sum);

	/**
	 * 获取产品
	 * 
	 * @param id
	 * @return
	 */
	Product getProduct(String id);

	/**
	 * 搜索产品
	 * 
	 * @param keywords
	 * @param option
	 * @return
	 */
	ResultPage<Product> searchProducts(String merchant, String keywords, int option);

	/**
	 * 创建交易
	 * 
	 * @param product 产品
	 * @param num     数量
	 * @return
	 * @throws TradeException
	 */
	Trade createTrade(Product product, int num) throws TradeException;

	/**
	 * 获取交易
	 * 
	 * @param id
	 * @return
	 */
	Trade getTrade(String id);

	/**
	 * 创建图片
	 * 
	 * @param product
	 * @return
	 */
	Picture createPicture(Product product);

	/**
	 * 获取图片
	 * 
	 * @param pid
	 * @return
	 */
	Picture getPicture(String pid);

}
