package cn.weforward.product.weforward.view;

import java.util.List;

import cn.weforward.common.util.TransList;
import cn.weforward.product.Picture;
import cn.weforward.product.Product;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 产品视图
 * 
 * @author daibo
 *
 */
@DocObject(description = "产品视图")
public class ProductView {

	protected Product m_Product;

	public static ProductView valueOf(Product product) {
		return null == product ? null : new ProductView(product);
	}

	public ProductView(Product product) {
		m_Product = product;
	}

	@DocAttribute(description = "产品id")
	public String getId() {
		return m_Product.getId().getOrdinal();
	}

	@DocAttribute(description = "产品名称")
	public String getName() {
		return m_Product.getName();
	}

	@DocAttribute(description = "产品价格，单位分")
	public int getPrice() {
		return m_Product.getPrice();
	}

	@DocAttribute(description = "剩余库存")
	public int getRemaining() {
		return m_Product.getRemaining();
	}

	@DocAttribute(description = "总库存")
	public int getSum() {
		return m_Product.getSum();
	}

	@DocAttribute(description = "描述")
	public String getDesc() {
		return m_Product.getDesc();
	}

	@DocAttribute(description = "产品状态")
	public int getState() {
		return m_Product.getState().id;
	}

	@DocAttribute(description = "产品状态描述")
	public String getStateDesc() {
		return m_Product.getState().getName();
	}

	@DocAttribute(description = "产品图片")
	public List<PictureView> getPictures() {
		return new TransList<PictureView, Picture>(m_Product.getPictures()) {

			@Override
			protected PictureView trans(Picture src) {
				return PictureView.valueOf(src);
			}
		};
	}
}
