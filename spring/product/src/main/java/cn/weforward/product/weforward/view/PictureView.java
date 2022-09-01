package cn.weforward.product.weforward.view;

import cn.weforward.product.Picture;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocObject;

/**
 * 
 * @author daibo
 *
 */
@DocObject(description = "图片视图")
public class PictureView {

	protected Picture m_Picture;

	public static PictureView valueOf(Picture picture) {
		return null == picture ? null : new PictureView(picture);
	}

	public PictureView(Picture picture) {
		m_Picture = picture;
	}

	@DocAttribute(description = "图片id", type = String.class)
	public String getId() {
		return m_Picture.getId().getId();
	}

}
