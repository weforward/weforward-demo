package cn.weforward.order.impl;

import javax.annotation.Resource;

import cn.weforward.order.Goods;
import cn.weforward.order.Trade;

/**
 * 交易vo
 * 
 * @author daibo
 *
 */
public class TradeVo implements Trade {
	@Resource
	public String id;
	@Resource
	public int amount;
	@Resource
	public int num;
	@Resource
	public GoodsVo goods;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public int getNum() {
		return num;
	}

	@Override
	public Goods getGoods() {
		return goods;
	}

}
