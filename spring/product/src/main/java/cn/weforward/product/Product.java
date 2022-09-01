package cn.weforward.product;

import java.util.List;

import cn.weforward.common.NameItem;
import cn.weforward.common.NameItems;
import cn.weforward.data.UniteId;

/**
 * 产品
 * 
 * @author daibo
 *
 */
public interface Product {
	/** 状态-下架中 */
	NameItem STATE_DOWN = NameItem.valueOf("下架中", 1);
	/** 状态-上架中 */
	NameItem STATE_UP = NameItem.valueOf("上架中", 2);
	/** 状态-已删除 */
	NameItem STATE_DELETE = NameItem.valueOf("已删除", 3);
	/** 状态-全部 */
	NameItems STATES = NameItems.valueOf(STATE_DOWN, STATE_UP, STATE_DELETE);

	/**
	 * 唯一id
	 * 
	 * @return
	 */
	UniteId getId();

	/**
	 * 对应商家
	 * 
	 * @return
	 */
	String getMerchant();

	/**
	 * 名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 名称
	 * 
	 * @param name
	 */
	void setName(String name);

	/**
	 * 描述
	 * 
	 * @return
	 */
	String getDesc();

	/**
	 * 描述
	 * 
	 * @param desc
	 */
	void setDesc(String desc);

	/**
	 * 价格
	 * 
	 * @return
	 */
	int getPrice();

	/**
	 * 设置价格
	 * 
	 * @param price
	 */
	void setPrice(int price);

	/**
	 * 剩余库存
	 * 
	 * @return
	 */
	int getRemaining();

	/**
	 * 设置库存
	 * 
	 * @param sum
	 */
	void setSum(int sum);

	/**
	 * 库存总量
	 * 
	 * @return
	 */
	int getSum();

	/**
	 * 添加图片
	 * 
	 * @param picture
	 */
	void addPicture(Picture picture);

	/**
	 * 获取图片
	 * 
	 * @return
	 */
	List<Picture> getPictures();

	/**
	 * 上架
	 */
	void up();

	/**
	 * 下架
	 */
	void down();

	/**
	 * 删除
	 */
	void delete();

	/**
	 * 状态
	 * 
	 * @return
	 */
	NameItem getState();
}
