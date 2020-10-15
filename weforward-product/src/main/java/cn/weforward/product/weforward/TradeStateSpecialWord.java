package cn.weforward.product.weforward;

import org.springframework.stereotype.Component;

import cn.weforward.framework.doc.AbstractDocSpecialWord;
import cn.weforward.product.Trade;

/**
 * 交易状态规则
 * 
 * @author daibo
 *
 */
@Component
public class TradeStateSpecialWord extends AbstractDocSpecialWord {

	public TradeStateSpecialWord() {
		super("交易状态", null);
		setTabelHeader("交易状态id", "交易状态名称");
		addTableItem(Trade.STATES);
	}
}
