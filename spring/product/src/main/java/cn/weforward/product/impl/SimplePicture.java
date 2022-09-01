package cn.weforward.product.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import cn.weforward.common.crypto.Base64;
import cn.weforward.common.io.BytesOutputStream;
import cn.weforward.data.UniteId;
import cn.weforward.data.persister.support.AbstractPersistent;
import cn.weforward.product.Picture;
import cn.weforward.product.Product;
import cn.weforward.product.di.ProductDi;
import cn.weforward.product.util.ImageHelper;
import cn.weforward.product.util.ImageInfo;

/**
 * 简单的图片实现
 * 
 * @author daibo
 *
 */
public class SimplePicture extends AbstractPersistent<ProductDi> implements Picture {
	@Resource(type = String.class)
	protected UniteId m_Product;
	@Resource
	protected String m_Type;
	@Resource
	protected String m_ContentBase64;

	protected SimplePicture(ProductDi di) {
		super(di);
	}

	public SimplePicture(ProductDi di, Product product) {
		super(di);
		genPersistenceId();
		m_Product = product.getId();
		persistenceUpdateNow();
	}

	@Override
	public UniteId getId() {
		return getPersistenceId();
	}

	@Override
	public void upload(InputStream input) throws IOException {
		BytesOutputStream out = new BytesOutputStream(input);
		cn.weforward.common.util.Bytes bytes = out.getBytes();
		out.close();
		out = null;
		int size = bytes.getSize();
		try (ByteArrayInputStream in = new ByteArrayInputStream(bytes.getBytes(), bytes.getOffset(), size)) {
			ImageInfo info = ImageHelper.getImageInfo(in);
			if (null == info) {
				throw new IOException("无法解析图片:" + getId());
			}
			m_Type = info.getType();
			m_ContentBase64 = Base64.encode(bytes.fit());
			getBusinessDi().getProduct(m_Product).addPicture(this);
			markPersistenceUpdate();
		}

	}

	@Override
	public String getName() {
		return getId().getId();
	}

	@Override
	public String getType() {
		return m_Type;
	}

	@Override
	public InputStream getStream() {
		return new ByteArrayInputStream(Base64.decode(m_ContentBase64));
	}

}
