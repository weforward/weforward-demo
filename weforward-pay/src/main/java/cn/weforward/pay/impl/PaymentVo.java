package cn.weforward.pay.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.weforward.data.annotation.ResourceExt;

/**
 * 支付单vo
 * 
 * @author daibo
 *
 */
public class PaymentVo {
	/** 金额(分) */
	@Resource
	public int amount;
	/** 超时时间(秒) */
	@Resource
	public int timeout;
	/** 状态 */
	@Resource
	public int state;
	/** 创建时间 */
	@Resource
	public Date createTime;
	/** 提交时间 */
	@Resource
	public Date submitTime;
	/** 结束时间 */
	@Resource
	public Date finishedTime;
	/** 支付渠道 */
	@ResourceExt(component = PayChannel.class)
	public List<PayChannel> channels;
	/** 回调uri */
	@Resource
	public String callback;
	/** 用户 */
	@Resource
	public String user;

	public PaymentVo() {

	}
}
