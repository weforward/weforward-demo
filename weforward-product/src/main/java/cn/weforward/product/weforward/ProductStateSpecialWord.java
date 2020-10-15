package cn.weforward.product.weforward;

import org.springframework.stereotype.Component;

import cn.weforward.framework.doc.AbstractDocSpecialWord;
import cn.weforward.product.Product;

/**
 * 产品状态规则
 * 
 * @author daibo
 *
 */
@Component
public class ProductStateSpecialWord extends AbstractDocSpecialWord {

	public ProductStateSpecialWord() {
		super("产品状态", null);
		setTabelHeader("产品状态id", "产品状态名称");
		addTableItem(Product.STATES);
	}
}
