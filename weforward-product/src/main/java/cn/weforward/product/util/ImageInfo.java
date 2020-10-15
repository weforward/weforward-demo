package cn.weforward.product.util;

/**
 * 图片基本信息
 * 
 * @author daibo
 * 
 */
public class ImageInfo {
	private int m_Width;
	private int m_Height;
	private String m_Type;

	protected ImageInfo(int w, int h, String type) {
		m_Width = w;
		m_Height = h;
		m_Type = type;
	}

	public int getWidth() {
		return m_Width;
	}

	public int getHeight() {
		return m_Height;
	}

	public String getType() {
		return m_Type;
	}
}
