package cn.weforward.order.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.weforward.data.annotation.Index;
import cn.weforward.data.annotation.ResourceExt;

/**
 * 订单vo对象
 * 
 * @author daibo
 *
 */
public class OrderVo {
	/** 用户 */
	@Index
	@Resource
	public String user;
	/** 创建时间 */
	@Resource
	public Date createTime;
	/** 状态 */
	@Resource
	public int state;
	/** 金额 */
	@Resource
	public int amount;
	/** 交易 */
	@ResourceExt(component = TradeVo.class)
	public List<TradeVo> trades = Collections.emptyList();
	/** 支付单 */
	@Index
	@Resource
	public String payment;
}
