package cn.weforward.order.di;

import cn.weforward.common.ResultPage;
import cn.weforward.common.util.TaskExecutor;
import cn.weforward.data.UniteId;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.data.persister.BusinessDi;
import cn.weforward.order.kit.PayMethods;
import cn.weforward.order.kit.TradeMethods;

/**
 * 订单依赖di
 * 
 * @author daibo
 *
 */
public interface OrderDi extends BusinessDi {

	/**
	 * 任务执行器
	 * 
	 * @return
	 */
	TaskExecutor getTaskExecutor();

	/**
	 * 交易方法集
	 * 
	 * @return
	 */
	TradeMethods getTradeMethods();

	/**
	 * 支付方法集
	 * 
	 * @return
	 */
	PayMethods getPayMethods();

	/**
	 * 写日志
	 * 
	 * @param id
	 * @param action
	 * @param what
	 * @param note
	 */
	void writeLog(UniteId id, String action, String what, String note);

	/**
	 * 获取日志
	 * 
	 * @param id
	 * @return
	 */
	ResultPage<BusinessLog> getLogs(UniteId id);

	/**
	 * 订单回调地址
	 * 
	 * @return
	 */
	String getOrderCallback();

}
