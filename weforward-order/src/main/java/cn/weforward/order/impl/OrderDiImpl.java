package cn.weforward.order.impl;

import java.util.Date;

import cn.weforward.common.ResultPage;
import cn.weforward.common.util.TaskExecutor;
import cn.weforward.common.util.TimeUtil;
import cn.weforward.data.UniteId;
import cn.weforward.data.log.BusinessLog;
import cn.weforward.data.log.BusinessLogger;
import cn.weforward.data.log.BusinessLoggerFactory;
import cn.weforward.data.persister.Persistent;
import cn.weforward.data.persister.Persister;
import cn.weforward.data.persister.PersisterFactory;
import cn.weforward.framework.WeforwardSession;
import cn.weforward.order.di.OrderDi;
import cn.weforward.order.kit.PayMethods;
import cn.weforward.order.kit.TradeMethods;
import cn.weforward.protocol.ops.User;

/**
 * 订单依赖di实现
 * 
 * @author daibo
 *
 */
public class OrderDiImpl implements OrderDi {
	/** 持久工厂类 */
	protected PersisterFactory m_PersisterFactory;
	/** 订单持久类 */
	protected Persister<SimpleOrder> m_PsOrder;
	/** 记录订单日志 */
	protected BusinessLogger m_OrderLogger;
	/** 交易方法集 */
	protected TradeMethods m_TradeMethods;
	/** 支付方法集 */
	protected PayMethods m_PayMethods;
	/** 任务执行器 */
	protected TaskExecutor m_TaskExecutor;
	/** 回调地址 */
	protected String m_OrderCallback;

	public OrderDiImpl(PersisterFactory persisterFactory, BusinessLoggerFactory loggerFactory) {
		m_PersisterFactory = persisterFactory;
		m_PsOrder = persisterFactory.createPersister(SimpleOrder.class, this);
		m_OrderLogger = loggerFactory.createLogger("order_log");
	}

	public void setTaskExecutor(TaskExecutor exe) {
		m_TaskExecutor = exe;
		m_TaskExecutor.execute(new Runnable() {

			@Override
			public void run() {
				Date begin = new Date(System.currentTimeMillis() - TimeUtil.HOUR_MILLS);
				ResultPage<SimpleOrder> rp = m_PsOrder.search(begin, null);
				for (int i = 1; rp.gotoPage(i); i++) {
					while (rp.hasNext()) {
						SimpleOrder order = rp.next();
						if (null == order || !order.iDo()) {
							continue;
						}
						order.reinit();
					}
				}
			}

		}, TaskExecutor.OPTION_NONE, 3 * 60 * 1000);
	}

	public void setTradeMethods(TradeMethods methods) {
		m_TradeMethods = methods;
	}

	public void setPayMethods(PayMethods methods) {
		m_PayMethods = methods;
	}

	public void setOrderCallback(String callback) {
		m_OrderCallback = callback;
	}

	@Override
	public TaskExecutor getTaskExecutor() {
		return m_TaskExecutor;
	}

	@Override
	public PayMethods getPayMethods() {
		return m_PayMethods;
	}

	@Override
	public TradeMethods getTradeMethods() {
		return m_TradeMethods;
	}

	@Override
	public String getOrderCallback() {
		return m_OrderCallback;
	}

	@Override
	public void writeLog(UniteId id, String action, String what, String note) {
		User user = WeforwardSession.TLS.getOperator();
		String author = null == user ? "system" : user.getName();
		m_OrderLogger.writeLog(id.getId(), author, action, what, note);
	}

	@Override
	public ResultPage<BusinessLog> getLogs(UniteId id) {
		return m_OrderLogger.getLogs(id.getId());
	}

	@Override
	public <E extends Persistent> Persister<E> getPersister(Class<E> clazz) {
		return m_PersisterFactory.getPersister(clazz);
	}

}
