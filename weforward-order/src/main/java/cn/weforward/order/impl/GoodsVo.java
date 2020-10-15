package cn.weforward.order.impl;

import javax.annotation.Resource;

import cn.weforward.order.Goods;

/**
 * 商品vo
 * 
 * @author daibo
 *
 */
public class GoodsVo implements Goods {
	@Resource
	public String id;
	@Resource
	public int price;
	@Resource
	public String name;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getPrice() {
		return price;
	}

	@Override
	public String getName() {
		return name;
	}

}
