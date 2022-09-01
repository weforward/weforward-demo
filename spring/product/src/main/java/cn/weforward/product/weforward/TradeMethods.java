package cn.weforward.product.weforward;

import javax.annotation.Resource;

import cn.weforward.framework.ApiException;
import cn.weforward.framework.ExceptionHandler;
import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.framework.doc.DocMethods;
import cn.weforward.framework.exception.ForwardException;
import cn.weforward.framework.util.ValidateUtil;
import cn.weforward.product.Product;
import cn.weforward.product.ProductService;
import cn.weforward.product.Trade;
import cn.weforward.product.TradeException;
import cn.weforward.product.weforward.view.TradeParam;
import cn.weforward.product.weforward.view.TradeView;
import cn.weforward.protocol.Access;
import cn.weforward.protocol.client.util.IdBean;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocMethod;
import cn.weforward.protocol.doc.annotation.DocParameter;

/**
 * 交易方法集
 * 
 * @author daibo
 *
 */
@DocMethods(index = 200)
@WeforwardMethods(kind = Access.KIND_SERVICE) // 交易只允许其它服务调用，哪些服务可调用交由网关控制
public class TradeMethods implements ExceptionHandler {

	@Resource
	protected ProductService m_ProductService;

	@DocMethod(description = "创建交易", index = 0)
	@WeforwardMethod
	public TradeView create(TradeParam params) throws ApiException, TradeException {
		String pid = params.getProductId();
		int num = params.getNum();
		ValidateUtil.isEmpty(pid, "产品id不能为空");
		ValidateUtil.ltOrEqZero(num, "数量不能小于0");
		Product product = m_ProductService.getProduct(pid);
		ValidateUtil.isEmpty(product, "找不到[" + pid + "]对应的产品");
		ForwardException.forwardToIfNeed(product);
		Trade trade = m_ProductService.createTrade(product, num);
		return TradeView.valueOf(trade);
	}

	@DocMethod(description = "获取交易", index = 1)
	@DocParameter(@DocAttribute(description = "交易id", type = String.class, name = "id"))
	@WeforwardMethod
	public TradeView get(IdBean id) throws ApiException {
		ValidateUtil.isEmpty(id.getId(), "交易id不能为空");
		Trade trade = m_ProductService.getTrade(id.getId());
		ValidateUtil.isEmpty(trade, "找不到[" + id.getId() + "]对应的交易");
		// 因为库存是按产品分的，所以要根据产品回源才能保存库存不会超出
		ForwardException.forwardToIfNeed(trade.getProduct());
		return TradeView.valueOf(trade);
	}

	@DocMethod(description = "锁定交易", index = 2)
	@DocParameter(@DocAttribute(description = "交易id", type = String.class, name = "id"))
	@WeforwardMethod
	public TradeView lock(IdBean id) throws ApiException, TradeException {
		ValidateUtil.isEmpty(id.getId(), "交易id不能为空");
		Trade trade = m_ProductService.getTrade(id.getId());
		ForwardException.forwardToIfNeed(trade.getProduct());
		trade.lock();
		return TradeView.valueOf(trade);
	}

	@DocMethod(description = "解锁交易", index = 3)
	@DocParameter(@DocAttribute(description = "交易id", type = String.class, name = "id"))
	@WeforwardMethod
	public TradeView unlock(IdBean id) throws ApiException, TradeException {
		ValidateUtil.isEmpty(id.getId(), "交易id不能为空");
		Trade trade = m_ProductService.getTrade(id.getId());
		ForwardException.forwardToIfNeed(trade.getProduct());
		trade.unlock();
		return TradeView.valueOf(trade);
	}

	@DocMethod(description = "确认交易", index = 4)
	@DocParameter(@DocAttribute(description = "交易id", type = String.class, name = "id"))
	@WeforwardMethod
	public TradeView confirm(IdBean id) throws ApiException, TradeException {
		ValidateUtil.isEmpty(id.getId(), "交易id不能为空");
		Trade trade = m_ProductService.getTrade(id.getId());
		ForwardException.forwardToIfNeed(trade.getProduct());
		trade.confirm();
		return TradeView.valueOf(trade);
	}

	@Override
	public Throwable exception(Throwable error) {
		if (error instanceof TradeException) {
			return new ApiException(ProductServiceCodes.getCode((TradeException) error), error.getMessage());
		}
		return error;
	}

}
