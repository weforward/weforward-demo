package cn.weforward.product.weforward;

import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.weforward.common.ResultPage;
import cn.weforward.common.util.StringUtil;
import cn.weforward.common.util.TimeUtil;
import cn.weforward.common.util.TransResultPage;
import cn.weforward.framework.ApiException;
import cn.weforward.framework.KeepServiceOrigin;
import cn.weforward.framework.ResourceDownloader;
import cn.weforward.framework.ResourceException;
import cn.weforward.framework.ResourceUploader;
import cn.weforward.framework.WeforwardFile;
import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.framework.WeforwardResource;
import cn.weforward.framework.doc.DocMethods;
import cn.weforward.framework.support.Global;
import cn.weforward.framework.util.ValidateUtil;
import cn.weforward.framework.util.WeforwardResourceHelper;
import cn.weforward.product.Picture;
import cn.weforward.product.Product;
import cn.weforward.product.ProductService;
import cn.weforward.product.weforward.view.PictureView;
import cn.weforward.product.weforward.view.ProductParam;
import cn.weforward.product.weforward.view.ProductView;
import cn.weforward.product.weforward.view.SearchProductParam;
import cn.weforward.product.weforward.view.UpdateProductParam;
import cn.weforward.protocol.client.util.IdBean;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocMethod;
import cn.weforward.protocol.doc.annotation.DocParameter;
import cn.weforward.protocol.doc.annotation.DocReturn;
import cn.weforward.protocol.support.datatype.FriendlyObject;

/**
 * Hello world!
 *
 */
@DocMethods(index = 100)
@WeforwardMethods
public class ProductMethods implements ResourceUploader, ResourceDownloader {

	final static Logger _Logger = LoggerFactory.getLogger(ProductMethods.class);

	@Resource
	protected ProductService m_ProductService;

	static final String MARK_PICTURE = "picture:";

	/**
	 * 获取为图片类型资源id
	 * 
	 * @param id
	 * @return
	 */
	static String wrapPictureId(String id) {
		if (null == id) {
			return null;
		}
		if (id.startsWith(MARK_PICTURE)) {
			return id;
		}
		return MARK_PICTURE + id;
	}

	private String getMerchant() {
		String merchant = Global.TLS.getValue("merchant");
		if (null == merchant) {
			merchant = "ffff";
		}
		if (merchant.length() != 4) {
			throw new IllegalArgumentException("商家固定要4位");
		}
		return merchant;

	}

	@WeforwardMethod(/* kind = Access.KIND_USER //demo实现不考虑权限问题 */)
	@DocMethod(description = "创建产品", index = 0)
	public ProductView create(ProductParam params) throws ApiException {
		String name = params.getName();
		String desc = params.getDesc();
		int price = params.getPrice();
		int sum = params.getSum();
		ValidateUtil.isEmpty(name, "产品名称不能为空");
		ValidateUtil.ltOrEqZero(price, "产品价格必须大于0");
		ValidateUtil.ltOrEqZero(sum, "产品库存必须大于0");
		Product product = m_ProductService.createProduct(getMerchant(), name, desc, price, sum);
		return ProductView.valueOf(product);
	}

	@KeepServiceOrigin
	@WeforwardMethod
	@DocParameter(@DocAttribute(name = "id", type = String.class, necessary = true, description = "产品id"))
	@DocMethod(description = "获取产品", index = 1)
	public ProductView get(FriendlyObject params) throws ApiException {
		return ProductView.valueOf(check(m_ProductService.getProduct(params.getString("id"))));
	}

	@KeepServiceOrigin
	@WeforwardMethod
	@DocMethod(description = "更新产品", index = 2)
	public ProductView update(UpdateProductParam params) throws ApiException {
		Product product = m_ProductService.getProduct(params.getId());
		product = check(product);
		if (null == product) {
			return null;
		}
		String name = params.getName();
		if (!StringUtil.isEmpty(name)) {
			product.setName(name);
		}
		String desc = params.getDesc();
		if (!StringUtil.isEmpty(desc)) {
			product.setDesc(desc);
		}
		int price = params.getPrice();
		if (price > 0) {
			product.setPrice(price);
		}
		int sum = params.getSum();
		if (sum > 0) {
			product.setSum(sum);
		}
		return ProductView.valueOf(product);
	}

	@KeepServiceOrigin
	@WeforwardMethod
	@DocMethod(description = "搜索产品", index = 3)
	public ResultPage<ProductView> search(SearchProductParam params) throws ApiException {
		ResultPage<Product> rp = m_ProductService.searchProducts(getMerchant(), params.getKeywords(),
				params.getOption());
		return new TransResultPage<ProductView, Product>(rp) {

			@Override
			protected ProductView trans(Product src) {
				return ProductView.valueOf(src);
			}
		};
	}

	@KeepServiceOrigin
	@WeforwardMethod
	@DocParameter(@DocAttribute(name = "id", type = String.class, description = "产品id"))
	@DocMethod(description = "上架产品", index = 4)
	public ProductView up(IdBean params) throws ApiException {
		Product product = m_ProductService.getProduct(params.getId());
		product = check(product);
		if (null == product) {
			return null;
		}
		product.up();
		return ProductView.valueOf(product);
	}

	@KeepServiceOrigin
	@WeforwardMethod
	@DocParameter(@DocAttribute(name = "id", type = String.class, description = "产品id"))
	@DocMethod(description = "上架产品", index = 5)
	public ProductView down(IdBean params) throws ApiException {
		Product product = m_ProductService.getProduct(params.getId());
		product = check(product);
		if (null == product) {
			return null;
		}
		product.down();
		return ProductView.valueOf(product);
	}

	@KeepServiceOrigin
	@WeforwardMethod
	@DocParameter(@DocAttribute(name = "id", type = String.class, description = "产品id"))
	@DocMethod(description = "删除产品", index = 6)
	public ProductView delete(IdBean params) throws ApiException {
		Product product = m_ProductService.getProduct(params.getId());
		product = check(product);
		if (null == product) {
			return null;
		}
		product.delete();
		return ProductView.valueOf(product);
	}

	@KeepServiceOrigin
	@DocMethod(description = "上传产品图片", index = 7)
	@DocParameter({ @DocAttribute(name = "id", description = "商品id", necessary = true, type = String.class) })
	@DocReturn(description = "产品图片信息", type = WeforwardResource.class)
	@WeforwardMethod
	public WeforwardResource uploadPicture(FriendlyObject params) throws ApiException {
		String id = params.getString("id");
		Product product = m_ProductService.getProduct(id);
		Picture p = m_ProductService.createPicture(product);
		String pictureId = p.getId().getId();
		String resource = wrapPictureId(pictureId);
		return WeforwardResourceHelper.valueOf(resource, System.currentTimeMillis() + TimeUtil.HOUR_MILLS,
				new PictureView(p));
	}

	@Override
	public boolean saveFile(String resourceId, WeforwardFile... files) throws IOException, ResourceException {
		if (resourceId.startsWith(MARK_PICTURE)) {
			String pid = resourceId.substring(MARK_PICTURE.length());
			Picture picture = m_ProductService.getPicture(pid);
			if (null == picture) {
				return false;
			}
			WeforwardFile file = files[0];
			picture.upload(file.getStream());
			return true;
		}
		return false;
	}

	@KeepServiceOrigin
	@DocMethod(description = "获取产品图片", index = 8)
	@DocParameter({ @DocAttribute(name = "id", description = "图片id", necessary = true, type = String.class) })
	@DocReturn(description = "产品图片信息", type = WeforwardResource.class)
	@WeforwardMethod
	public WeforwardResource getPicture(FriendlyObject params) throws ApiException {
		String id = params.getString("id");
		Picture p = m_ProductService.getPicture(id);
		String pictureId = p.getId().getId();
		String resource = wrapPictureId(pictureId);
		return WeforwardResourceHelper.valueOf(resource, System.currentTimeMillis() + TimeUtil.HOUR_MILLS,
				new PictureView(p));
	}

	@Override
	public WeforwardFile findFile(String resourceId) throws IOException, ResourceException {
		if (resourceId.startsWith(MARK_PICTURE)) {
			String pid = resourceId.substring(MARK_PICTURE.length());
			Picture picture = m_ProductService.getPicture(pid);
			return WeforwardResourceHelper.newFile(picture.getName(), picture.getStream(), picture.getType());
		}
		return null;
	}

	private Product check(Product product) {
		if (null == product) {
			return null;
		}
		if (!StringUtil.eq(product.getMerchant(), getMerchant())) {
			return null;
		}
		return product;
	}

}
