package cn.weforward.product.util;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * 图片处理辅助类
 * 
 * @author zhangpengji
 * 
 */
public class ImageHelper {

	/**
	 * 获取图片信息
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static ImageInfo getImageInfo(InputStream in) throws IOException {
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(in);
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);
			if (!iterator.hasNext()) {
				return null;
			}
			ImageReader reader = iterator.next();
			reader.setInput(iis, true);
			String type = reader.getFormatName();
			int width = reader.getWidth(0);
			int height = reader.getHeight(0);
			reader.dispose();
			return new ImageInfo(width, height, type);
		} finally {
			if (null != iis) {
				try {
					iis.close();
				} catch (Throwable e) {
				}
			}
		}
	}

	/**
	 * 获取图片信息
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static ImageInfo getImageInfo(byte[] data) throws IOException {
		ByteArrayInputStream bis = null;
		try {
			bis = new ByteArrayInputStream(data);
			return ImageHelper.getImageInfo(bis);
		} finally {
			try {
				bis.close();
			} catch (Throwable e) {
			}
		}
	}

	/**
	 * 多张图片合成一张图片，随机平铺
	 * 
	 * @param ins       图片集
	 * @param mergeW    合成后图片的宽度
	 * @param mergeH    合成后图片的高度
	 * @param originalW 源图片的宽度
	 * @param originalH 源图片的高度
	 * @return
	 * @throws IOException
	 */
	public static InputStream mergeImage(List<InputStream> ins, int mergeW, int mergeH, int originalW, int originalH)
			throws IOException {
		List<BufferedImage> images = new ArrayList<BufferedImage>(ins.size());
		for (InputStream in : ins) {
			BufferedImage bufferedImage;
			bufferedImage = ImageIO.read(in);
			images.add(bufferedImage);
		}

		Random rd = new Random();
		int width = mergeW - (mergeW % originalW);
		int height = mergeH - (mergeH % originalH);
		// 画图
		BufferedImage backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = backgroundImage.createGraphics();
		for (int i = 0; i < mergeW / originalW; i++) {
			for (int j = 0; j < mergeH / originalH; j++) {
				int index = rd.nextInt(images.size());
				g.drawImage(images.get(index), i * originalW, j * originalH, originalW, originalH, null);
			}

		}
		// 输出
		byte[] imageInByte = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(backgroundImage, "jpg", baos);
		imageInByte = baos.toByteArray();
		baos.close();
		ByteArrayInputStream bin = new ByteArrayInputStream(imageInByte);
		return bin;
	}

	/**
	 * 转换为JPG图片
	 *
	 */
	public static void convertToJpeg(InputStream in, OutputStream out) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(in);
		ImageIO.write(bufferedImage, "jpg", out);
	}

	/**
	 * @param in 原始图像
	 * @param w  缩小的宽度
	 * @param h  缩小的高度
	 * @return 返回处理后的图像
	 * @throws IOException
	 */
	public static InputStream zoomImage(InputStream in, int w, int h) throws IOException {
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(in);
			ImageReader ir = null;
			Iterator<ImageReader> irs = ImageIO.getImageReaders(iis);
			if (irs.hasNext()) {
				ir = irs.next();
			}
			BufferedImage im = ImageIO.read(iis);
			/* 调整后的图片的宽度和高度 */
			int toWidth = w;
			int toHeight = h;
			/* 新生成结果图片 */
			BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
			result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0,
					null);
			// 输出
			String formatName = (null != ir ? ir.getFormatName() : "jpg");
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(result, formatName, bos);
			return new ByteArrayInputStream(bos.toByteArray());
		} finally {
			if (null != iis) {
				try {
					iis.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 上传图片裁剪处理
	 * 
	 * @param inputStream
	 * @param w           宽度
	 * @param h           高度
	 * @return
	 * @throws IOException
	 */
	public static InputStream cutImage(InputStream inputStream, int w, int h) throws IOException {
		if (null == inputStream || 0 == w || 0 == h) {
			return null;
		}
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(inputStream);
			ImageReader ir = null;
			Iterator<ImageReader> irs = ImageIO.getImageReaders(iis);
			if (irs.hasNext()) {
				ir = irs.next();
			}
			BufferedImage bufferedImage;
			bufferedImage = ImageIO.read(iis);
			if (null == bufferedImage) {
				throw new IllegalArgumentException(inputStream + "输入流非图片资源！");
			}

			int nWidth = bufferedImage.getWidth();
			int nHeight = bufferedImage.getHeight();
			// 取缩放比例与截取
			int x = 0, y = 0;
			double dbScaleX = 1.0 * w / nWidth;
			double dbScaleY = 1.0 * h / nHeight;
			if (dbScaleX > dbScaleY) {
				// 按宽度比例缩放
				nHeight = (int) (h / dbScaleX);
				// 定位中间
			} else if (dbScaleY > dbScaleX) {
				// 按高度比例缩放
				nWidth = (int) (w / dbScaleY);
			}
			BufferedImage target = null;
			target = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics g = target.getGraphics();
			g.drawImage(bufferedImage, 0, 0, w, h, x, y, nWidth, nHeight, null);

			// 输出
			ByteArrayOutputStream rs = new ByteArrayOutputStream();
			String formatName = (null != ir ? ir.getFormatName() : "jpg");
			ImageIO.write(target, formatName, rs);
			ByteArrayInputStream bin = new ByteArrayInputStream(rs.toByteArray());
			return bin;
		} finally {
			if (null != iis) {
				try {
					iis.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 是否有透明
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static boolean hasAlpha(InputStream in) throws IOException {
		BufferedImage image = ImageIO.read(in);
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int rgb = image.getRGB(x, y);
				int alpha = (rgb >> 24) & 0xff;
				// System.out.print(Integer.toHexString(alpha >>> 4) + ""
				// + Integer.toHexString(alpha & 0xf) + ",");
				if (alpha != 0xff) {
					return true;
				}
			}
			// System.out.println();
		}
		return false;
	}

	/**
	 * 按w，h比例缩放并打图片水印
	 * 
	 * @return
	 * @throws IOException
	 */
	public static InputStream markImageByIcon(InputStream inputStream, InputStream iconInputStream, int w, int h)
			throws IOException {
		if (null == inputStream || null == iconInputStream || 0 == w || 0 == h) {
			return null;
		}
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(inputStream);
			ImageReader ir = null;
			Iterator<ImageReader> irs = ImageIO.getImageReaders(iis);
			if (irs.hasNext()) {
				ir = irs.next();
			}
			BufferedImage img = ImageIO.read(iis);
			if (null == img) {
				throw new IllegalArgumentException(inputStream + "输入流非图片资源！");
			}

			// 图片宽高
			int oldwidth = img.getWidth(null);
			int oldheight = img.getHeight(null);

			Image ic = ImageIO.read(iconInputStream);
			// icon宽高
			int icwidth = ic.getWidth(null);
			int icheight = ic.getHeight(null);

			double zoomw = 1.0 * w / oldwidth;
			double zoomh = 1.0 * h / oldheight;
			double zoom = zoomw > zoomh ? zoomw : zoomh;
			int width = (int) (oldwidth * zoom);
			int height = (int) (oldheight * zoom);

			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bi.createGraphics();
			// 水印坐标
			int x = 0;
			int y = 0;
			if (zoomw > zoomh) {
				x = width - icwidth;
				y = (height - h) / 2 + h - icheight;
			} else {
				x = (width - w) / 2 + w - icwidth;
				y = height - icheight;
			}
			g.drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

			float clarity = 0.6f;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, clarity));
			g.drawImage(ic, x, y, null);
			g.dispose();

			// 输出
			ByteArrayOutputStream rs = new ByteArrayOutputStream();
			String formatName = (null != ir ? ir.getFormatName() : "jpg");
			ImageIO.write(bi, formatName, rs);
			ByteArrayInputStream bin = new ByteArrayInputStream(rs.toByteArray());
			return bin;
		} finally {
			if (null != iis) {
				try {
					iis.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		File file = new File("1.png");
		InputStream in = new FileInputStream(file);

		ImageInfo info = getImageInfo(in);
		System.out.println(info);

		// InputStream zoom = zoomImage(in, 50, 50);
		// FileOutputStream out = new FileOutputStream(new File("zoom.jpg"));
		// byte[] buf = new byte[1024];
		// int len;
		// while (-1 != (len = zoom.read(buf))) {
		// out.write(buf, 0, len);
		// }
		// out.flush();
		// out.close();
		// zoom.close();

		// markImageByIcon(in, icin, 900, 500);

		// 测试iis是否会关闭source input
		// ImageInputStream iis = ImageIO.createImageInputStream(in);
		// iis.close();
		// System.out.println(in.read());

		// System.out.println(hasAlpha(in));
	}
}
