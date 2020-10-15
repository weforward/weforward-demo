package cn.weforward.product.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import cn.weforward.common.NameItem;
import cn.weforward.common.util.FreezedList;
import cn.weforward.common.util.StringUtil;
import cn.weforward.common.util.TransList;
import cn.weforward.data.UniteId;
import cn.weforward.data.persister.support.AbstractPersistent;
import cn.weforward.product.Picture;
import cn.weforward.product.Product;
import cn.weforward.product.di.ProductDi;

/**
 * 简单的产品实现
 * 
 * @author daibo
 *
 */
public class SimpleProduct extends AbstractPersistent<ProductDi> implements Product {
	/** 商家 */
	@Resource
	protected String m_Merchant;
	/** 名称 */
	@Resource
	protected String m_Name;
	/** 描述 */
	@Resource
	protected String m_Desc;
	/** 价格 */
	@Resource
	protected int m_Price;
	/** 总量 */
	@Resource
	protected int m_Sum;
	/** 图片 */
	@Resource
	protected List<String> m_Pictures;
	/** 状态 */
	@Resource
	protected int m_State;

	protected SimpleProduct(ProductDi di) {
		super(di);
	}

	public SimpleProduct(ProductDi di, String merchant, String name, String desc, int price, int sum) {
		super(di);
		genPersistenceId(merchant);
		m_Merchant = merchant;
		m_Name = name;
		m_Desc = desc;
		m_Price = price;
		m_Sum = sum;
		m_State = STATE_DOWN.id;
		markPersistenceUpdate();
	}

	@Override
	public UniteId getId() {
		return getPersistenceId();
	}

	@Override
	public String getMerchant() {
		return m_Merchant;
	}

	public void setName(String name) {
		if (StringUtil.eq(name, m_Name)) {
			return;
		}
		m_Name = name;
		markPersistenceUpdate();
	}

	@Override
	public void setSum(int sum) {
		if (m_Sum == sum) {
			return;
		}
		m_Sum = sum;
		markPersistenceUpdate();
	}

	@Override
	public String getName() {
		return m_Name;
	}

	@Override
	public String getDesc() {
		return m_Desc;
	}

	@Override
	public void setDesc(String desc) {
		m_Desc = desc;
	}

	@Override
	public void setPrice(int price) {
		if (m_Price == price) {
			return;
		}
		m_Price = price;
		markPersistenceUpdate();
	}

	@Override
	public int getPrice() {
		return m_Price;
	}

	@Override
	public int getRemaining() {
		return getSum() - (int) getBusinessDi().getProductCounter().get(genLockKey(getId()));
	}

	@Override
	public int getSum() {
		return m_Sum;
	}

	public static String genLockKey(UniteId id) {
		return "LOCK:" + id.getOrdinal();
	}

	@Override
	public synchronized void addPicture(Picture picture) {
		List<String> olds = m_Pictures;
		if (null == olds) {
			olds = Collections.emptyList();
		}
		m_Pictures = FreezedList.addToFreezed(olds, olds.size(), picture.getId().getId());
		markPersistenceUpdate();
	}

	@Override
	public List<Picture> getPictures() {
		if (null == m_Pictures) {
			return Collections.emptyList();
		}
		return new TransList<Picture, String>(m_Pictures) {

			@Override
			protected Picture trans(String src) {
				return getBusinessDi().getPicture(src);
			}
		};
	}

	@Override
	public synchronized void up() {
		m_State = STATE_UP.id;
		markPersistenceUpdate();
	}

	@Override
	public synchronized void down() {
		m_State = STATE_DOWN.id;
		markPersistenceUpdate();
	}

	@Override
	public synchronized void delete() {
		m_State = STATE_DELETE.id;
		markPersistenceUpdate();
	}

	@Override
	public NameItem getState() {
		return STATES.get(m_State);
	}

}
