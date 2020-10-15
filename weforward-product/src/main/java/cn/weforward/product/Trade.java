package cn.weforward.product;

import cn.weforward.common.NameItem;
import cn.weforward.common.NameItems;
import cn.weforward.data.UniteId;

/**
 * 交易
 * 
 * @author daibo
 *
 */
public interface Trade {
	/** 状态-新建 */
	NameItem STATE_NEW = NameItem.valueOf("新建", 0);
	/** 状态-锁定中 */
	NameItem STATE_LOCK = NameItem.valueOf("锁定中", 1);
	/** 状态-已解锁 */
	NameItem STATE_UNLOCK = NameItem.valueOf("已解锁", 2);
	/** 状态-已确认 */
	NameItem STATE_CONFIRM = NameItem.valueOf("已确认", 3);
	/** 状态-全部 */
	NameItems STATES = NameItems.valueOf(STATE_NEW, STATE_LOCK, STATE_UNLOCK, STATE_CONFIRM);

	/**
	 * id
	 * 
	 * @return
	 */
	UniteId getId();

	/**
	 * 交易金额
	 * 
	 * @return
	 */
	int getAmount();

	/**
	 * 产品数量
	 * 
	 * @return
	 */
	int getNum();

	/**
	 * 产品
	 * 
	 * @return
	 */
	Product getProduct();

	/**
	 * 锁定
	 */
	void lock() throws TradeException;

	/**
	 * 解锁
	 */
	void unlock() throws TradeException;

	/**
	 * 确认
	 */
	void confirm() throws TradeException;

	/**
	 * 状态
	 * 
	 * @return
	 */
	NameItem getState();

}
