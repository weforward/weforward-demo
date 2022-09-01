package cn.weforward.product.impl;

import cn.weforward.data.UniteId;
import cn.weforward.data.counter.Counter;
import cn.weforward.data.counter.CounterFactory;
import cn.weforward.data.persister.Persistent;
import cn.weforward.data.persister.Persister;
import cn.weforward.data.persister.PersisterFactory;
import cn.weforward.product.Picture;
import cn.weforward.product.Product;
import cn.weforward.product.di.ProductDi;

/**
 * 产品依赖di实现
 * 
 * @author daibo
 *
 */
public class ProductDiImpl implements ProductDi {

	protected PersisterFactory m_Factory;

	protected Counter m_ProductCounter;

	protected Persister<SimpleProduct> m_PsProduct;

	protected Persister<SimplePicture> m_PsPicture;

	protected Persister<SimpleTrade> m_PsTrade;

	public ProductDiImpl(PersisterFactory factory, CounterFactory counterFactory) {
		m_Factory = factory;
		m_PsProduct = m_Factory.createPersister(SimpleProduct.class, this);
		m_PsTrade = m_Factory.createPersister(SimpleTrade.class, this);
		m_PsPicture = m_Factory.createPersister(SimplePicture.class, this);
		m_ProductCounter = counterFactory.createCounter("product_counter");
	}

	@Override
	public <E extends Persistent> Persister<E> getPersister(Class<E> clazz) {
		return m_Factory.getPersister(clazz);
	}

	@Override
	public Counter getProductCounter() {
		return m_ProductCounter;
	}

	@Override
	public Product getProduct(UniteId id) {
		return m_PsProduct.get(id);
	}

	@Override
	public Picture getPicture(String id) {
		return m_PsPicture.get(id);
	}

}
