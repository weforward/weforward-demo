package cn.weforward.product.impl;

import javax.annotation.Resource;

import cn.weforward.common.NameItem;
import cn.weforward.data.UniteId;
import cn.weforward.data.counter.Counter;
import cn.weforward.data.persister.support.AbstractPersistent;
import cn.weforward.product.Product;
import cn.weforward.product.Trade;
import cn.weforward.product.TradeException;
import cn.weforward.product.di.ProductDi;
import cn.weforward.product.exception.InsufficientException;

/**
 * 简单的交易
 * 
 * @author daibo
 *
 */
public class SimpleTrade extends AbstractPersistent<ProductDi> implements Trade {
	/** 产品 */
	@Resource(type = String.class)
	protected UniteId m_Product;
	/** 数量 */
	@Resource
	protected int m_Num;
	/** 产品金额 */
	@Resource
	protected int m_Amount;
	/** 状态 */
	@Resource
	protected int m_State;

	protected SimpleTrade(ProductDi di) {
		super(di);
	}

	public SimpleTrade(ProductDi di, Product product, int num) {
		super(di);
		m_Product = product.getId();
		m_Num = num;
		m_State = STATE_NEW.id;
		m_Amount = product.getPrice() * num;
		persistenceUpdateNow();
	}

	@Override
	public UniteId getId() {
		return getPersistenceId();
	}

	@Override
	public int getAmount() {
		return m_Amount;
	}

	@Override
	public int getNum() {
		return m_Num;
	}

	@Override
	public synchronized void lock() throws TradeException {
		if (isState(STATE_LOCK) || isState(STATE_CONFIRM)) {
			return;// 幂等
		}
		if (isState(STATE_UNLOCK)) {
			throw new TradeException("已解锁不能再次锁定");
		}
		Counter c = getBusinessDi().getProductCounter();
		Product product = getProduct();
		int sum = product.getSum();
		int step = m_Num;
		String id = SimpleProduct.genLockKey(product.getId());
		long count = c.inc(id, step);
		if (count > sum) {
			c.inc(id, -step);
			throw new InsufficientException("库存不足");
		}
		m_State = STATE_LOCK.id;
		markPersistenceUpdate();
	}

	@Override
	public synchronized void unlock() throws TradeException {
		if (isState(STATE_UNLOCK) || isState(STATE_NEW)) {
			return;
		}
		if (isState(STATE_CONFIRM)) {
			throw new TradeException("已确认不能解锁");
		}
		Counter c = getBusinessDi().getProductCounter();
		int step = m_Num;
		Product product = getProduct();
		String id = SimpleProduct.genLockKey(product.getId());
		c.inc(id, -step);
		m_State = STATE_UNLOCK.id;
		markPersistenceUpdate();
	}

	@Override
	public synchronized void confirm() throws TradeException {
		if (isState(STATE_CONFIRM)) {
			return;
		}
		if (!isState(STATE_UNLOCK)) {
			throw new TradeException("未锁定不能确认");
		}
		m_State = STATE_CONFIRM.id;
		markPersistenceUpdate();
	}

	@Override
	public NameItem getState() {
		return STATES.get(m_State);
	}

	private boolean isState(NameItem ni) {
		return m_State == ni.id;
	}

	@Override
	public Product getProduct() {
		return getBusinessDi().getProduct(m_Product);
	}

}
