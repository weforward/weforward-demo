package cn.weforward.product.impl;

import java.util.ArrayList;
import java.util.List;

import cn.weforward.common.NameItem;
import cn.weforward.common.ResultPage;
import cn.weforward.common.util.ResultPageHelper;
import cn.weforward.common.util.StringUtil;
import cn.weforward.data.counter.CounterFactory;
import cn.weforward.data.persister.PersisterFactory;
import cn.weforward.product.Picture;
import cn.weforward.product.Product;
import cn.weforward.product.ProductService;
import cn.weforward.product.Trade;
import cn.weforward.product.TradeException;
import cn.weforward.product.exception.InsufficientException;

/**
 * 产品服务实现
 * 
 * @author daibo
 *
 */
public class ProductServiceImpl extends ProductDiImpl implements ProductService {

	public ProductServiceImpl(PersisterFactory factory, CounterFactory counterFactory) {
		super(factory, counterFactory);
	}

	@Override
	public Product createProduct(String merchant, String name, String desc, int price, int sum) {
		return new SimpleProduct(this, merchant, name, desc, price, sum);
	}

	@Override
	public Product getProduct(String id) {
		return m_PsProduct.get(id);
	}

	@Override
	public ResultPage<Product> searchProducts(String merchant, String keywords, int options) {
		ResultPage<? extends Product> rp = m_PsProduct.startsWith(merchant);
		List<Product> list = new ArrayList<>();
		// 产品不多的时候直接遍历过滤，多了就要考虑使用索引查询
		for (Product product : ResultPageHelper.toForeach(rp)) {
			if (!isMatch(product, keywords)) {
				continue;
			}
			if (!isMatch(product, options)) {
				continue;
			}
			list.add(product);
		}
		return ResultPageHelper.toResultPage(list);
	}

	@Override
	public Trade createTrade(Product product, int num) throws TradeException {
		if (product.getRemaining() < num) {
			throw new InsufficientException("产品库存不足");
		}
		return new SimpleTrade(this, product, num);
	}

	@Override
	public Trade getTrade(String id) {
		return m_PsTrade.get(id);
	}

	private static boolean isMatch(Product product, String keywords) {
		if (null == product) {
			return false;
		}
		String n = product.getName();
		if (StringUtil.isEmpty(n)) {
			return false;
		}
		return n.contains(keywords);
	}

	private static boolean isMatch(Product product, int options) {
		if (null == product) {
			return false;
		}
		NameItem state = product.getState();
		if (state.id == Product.STATE_DELETE.id) {
			return false;
		}
		if (OPTION_NONE == options) {
			return true;
		}
		if (OPTION_UP == options && state.id != Product.STATE_UP.id) {
			return false;
		}
		if (OPTION_DOWN == options && state.id != Product.STATE_DOWN.id) {
			return false;
		}
		return true;
	}

	@Override
	public Picture createPicture(Product product) {
		return new SimplePicture(this, product);
	}

	@Override
	public Picture getPicture(String pid) {
		return m_PsPicture.get(pid);
	}

}
