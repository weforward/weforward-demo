package cn.weforward.product;

import java.io.IOException;
import java.io.InputStream;

import cn.weforward.data.UniteId;

/**
 * 图片
 * 
 * @author daibo
 *
 */
public interface Picture {
	/**
	 * 图片id
	 * 
	 * @return
	 */
	UniteId getId();

	/**
	 * 上传
	 * 
	 * @param in
	 * @throws IOException
	 */
	void upload(InputStream in) throws IOException;

	String getName();

	String getType();

	InputStream getStream();

}
