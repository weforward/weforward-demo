package cn.weforward.pay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.weforward.common.json.JsonUtil;
import cn.weforward.common.util.FreezedList;
import cn.weforward.common.util.StringUtil;
import cn.weforward.common.util.UrlUtil;

/**
 * 类似表单的Map封装，提供名称排序、URL/JSON格式串等转换的功能
 * 
 * @author liangyi
 * 
 */
public class Form extends AbstractMap<String, String> {
	/** 日志记录器 */
	static final Logger _Logger = LoggerFactory.getLogger(Form.class);

	public static final char PREFIX = '#';

	/** 去除空值项 */
	public static final int OPTION_TRIM = 0x0001;
	/** 无选项 */
	public static final int OPTION_NONE = 0x0000;

	/** 以数组列表组织表单项 */
	protected final List<Entry> m_Entrys;
	/** entrySet 缓存 */
	protected Set<Map.Entry<String, String>> m_EntrySet;

	/**
	 * 转为表单集
	 * 
	 * @param map 字串K/V集合
	 * @return 转换后的表单集
	 */
	public static Form valueOf(Map<String, String> map) {
		if (map instanceof Form) {
			return (Form) map;
		}
		return new Form(map, OPTION_NONE);
	}

	/**
	 * 创建一个只有单项的表单
	 * 
	 * @param name  表单项名称
	 * @param value 表单项值
	 * @return 只有单项的表单
	 */
	public static Form singleton(String name, String value) {
		return new Form(name, value);
	}

	/**
	 * URL编码表单参数串（如：a=123&b=777&c=P%23）解拆为表单项
	 * 
	 * @param encodeUrl URL编码表单参数串，如：a=123&b=777&c=P%23
	 */
	public Form(String encodeUrl) {
		// String[] ss = encodeUrl.split("\\&");
		m_Entrys = new ArrayList<Entry>(16);
		int begin = 0;
		int end = encodeUrl.indexOf('&');
		do {
			if (-1 == end) {
				end = encodeUrl.length();
			}
			int idx = encodeUrl.indexOf('=', begin);
			if (-1 == idx) {
				// 晕，格式不对，当它为表单项名吧
				m_Entrys.add(new Entry(encodeUrl.substring(begin, end), ""));
			} else {
				try {
					String name = encodeUrl.substring(begin, idx).trim();
					// UPOP返回很不标准的格式“cupReserved={cardNumber=6212341111111111111&orderAmount=100&orderCurrency=156}”
					++idx;
					String value = encodeUrl.substring(idx, end).trim();
					if (value.length() > 0 && '{' == value.charAt(0)) {
						// 尝试找到对应的‘}’
						int enclose = encodeUrl.indexOf('}', idx);
						if (-1 != enclose && enclose > end
								&& ((1 + enclose) == encodeUrl.length() || '&' == encodeUrl.charAt(enclose + 1))) {
							// 越过end以找到的‘}’为结束位
							end = enclose + 1;
							value = encodeUrl.substring(idx, end).trim();
						}
					}
					value = URLDecoder.decode(value, "UTF-8");
					m_Entrys.add(new Entry(name, value));
				} catch (UnsupportedEncodingException e) {
					_Logger.warn("encodeUrl格式有误=>" + encodeUrl.substring(begin, end), e);
				}
			}
			begin = end + 1;
			end = encodeUrl.indexOf('&', begin);
		} while (begin < encodeUrl.length());
	}

	/**
	 * URL编码表单参数串（如：a=123&b=777&c=P%23）解拆为表单项
	 * 
	 * @param encodeUrl URL编码表单参数串，如：a=123&b=777&c=P%23
	 * @param decode    是否进行url解码
	 */

	public Form(String encodeUrl, boolean decode) {
		// String[] ss = encodeUrl.split("\\&");
		m_Entrys = new ArrayList<Entry>(16);
		int begin = 0;
		int end = encodeUrl.indexOf('&');
		do {
			if (-1 == end) {
				end = encodeUrl.length();
			}
			int idx = encodeUrl.indexOf('=', begin);
			if (-1 == idx) {
				// 晕，格式不对，当它为表单项名吧
				m_Entrys.add(new Entry(encodeUrl.substring(begin, end), ""));
			} else {
				try {
					String name = encodeUrl.substring(begin, idx).trim();
					// UPOP返回很不标准的格式“cupReserved={cardNumber=6212341111111111111&orderAmount=100&orderCurrency=156}”
					++idx;
					String value = encodeUrl.substring(idx, end).trim();
					if (value.length() > 0 && '{' == value.charAt(0)) {
						// 尝试找到对应的‘}’
						int enclose = encodeUrl.indexOf('}', idx);
						if (-1 != enclose && enclose > end
								&& ((1 + enclose) == encodeUrl.length() || '&' == encodeUrl.charAt(enclose + 1))) {
							// 越过end以找到的‘}’为结束位
							end = enclose + 1;
							value = encodeUrl.substring(idx, end).trim();
						}
					}
					if (decode) {
						value = URLDecoder.decode(value, "UTF-8");
					}
					m_Entrys.add(new Entry(name, value));
				} catch (UnsupportedEncodingException e) {
					_Logger.warn("encodeUrl格式有误=>" + encodeUrl.substring(begin, end), e);
				}
			}
			begin = end + 1;
			end = encodeUrl.indexOf('&', begin);
		} while (begin < encodeUrl.length());
	}

	static final boolean isEmpty(String str) {
		return (null == str || str.length() == 0);
	}

	/**
	 * 由Map转为表单
	 * 
	 * @param map    字串key/value集合
	 * @param option OPTION_TRIM/OPTION_NONE
	 */
	public Form(Map<String, String> map, int option) {
		if (map instanceof Form) {
			m_Entrys = new ArrayList<Entry>(((Form) map).m_Entrys);
		} else {
			m_Entrys = new ArrayList<Entry>(map.size());
			if (OPTION_TRIM == option) {
				// 去除value为空的项
				for (Map.Entry<String, String> e : map.entrySet()) {
					if (isEmpty(e.getValue())) {
						continue;
					}
					m_Entrys.add(new Entry(e));
				}
			} else {
				for (Map.Entry<String, String> e : map.entrySet()) {
					m_Entrys.add(new Entry(e));
				}
			}
		}
	}

	public Form(int initialCapacity) {
		m_Entrys = new ArrayList<Entry>(initialCapacity);
	}

	/**
	 * 构造一个空白的表单
	 */
	public Form() {
		m_Entrys = new ArrayList<Entry>();
	}

	private Form(String name, String value) {
		m_Entrys = new ArrayList<Entry>(1);
		put(name, value);
	}

	@Override
	public Set<Map.Entry<String, String>> entrySet() {
		if (null != m_EntrySet) {
			return m_EntrySet;
		}
		m_EntrySet = new AbstractSet<Map.Entry<String, String>>() {
			@Override
			public Iterator<Map.Entry<String, String>> iterator() {
				return new EntryIterator();
			}

			@Override
			public int size() {
				return m_Entrys.size();
			}
		};
		return m_EntrySet;
	}

	int indexOf(String key) {
		for (int i = m_Entrys.size() - 1; i >= 0; i--) {
			if (m_Entrys.get(i).equals(key)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void clear() {
		m_Entrys.clear();
	}

	@Override
	public String put(String key, String value) {
		int idx = indexOf(key);
		if (-1 == idx) {
			Entry e = new Entry(key, value);
			m_Entrys.add(e);
			return null;
		}
		Entry e = m_Entrys.get(idx);
		String old = e.getValue();
		e.setValue(value);
		return old;
	}

	@Override
	public String remove(Object key) {
		int idx = indexOf((String) key);
		if (-1 == idx) {
			return null;
		}
		String old = m_Entrys.get(idx).getValue();
		m_Entrys.remove(idx);
		return old;
	}

	@Override
	public int size() {
		return m_Entrys.size();
	}

	/**
	 * 表单项列表
	 */
	public List<Entry> items() {
		return FreezedList.freezed(m_Entrys);
	}

	protected final static Comparator<Entry> COMP = new Comparator<Entry>() {
		public int compare(Entry o1, Entry o2) {
			return o1.key.compareTo(o2.key);
		}
	};

	/**
	 * 按表单项名称重新排序
	 */
	public void sort() {
		Collections.sort(m_Entrys, COMP);
	}

	/**
	 * 去除空值的项
	 */
	public void trim() {
		// 去除value为空的项
		for (int i = m_Entrys.size() - 1; i >= 0; i--) {
			Entry e = m_Entrys.get(i);
			if (isEmpty(e.getValue())) {
				m_Entrys.remove(i);
			}
		}
	}

	/**
	 * 只取得参数部分（去除内置的特殊表单项，如ACTION）
	 * 
	 * @return 参数集
	 */
	public Map<String, String> getParams() {
		Form params = new Form(m_Entrys.size());
		for (int i = 0; i < m_Entrys.size(); i++) {
			Entry e = m_Entrys.get(i);
			if (e.key.length() > 0 && PREFIX == e.key.charAt(0)) {
				// 忽略特殊表单项
				continue;
			}
			params.m_Entrys.add(e);
		}
		return params;
	}

	@Override
	public String toString() {
		return toUri();
	}

	public String stringValue() {
		return toUri();
	}

	public static Form valueOf(String value) {
		return new Form(value);
	}

	/**
	 * 使用URI参数编码（UTF-8字符集）拼接表单项，例：a=123&b=777&c=P%23
	 * 
	 * @param sb    可变字串
	 * @param quote 是否使用双引号包裹值，如：a="123"&b="777"&c="P%23"
	 * @return 输入的sb
	 */
	public StringBuilder toUri(StringBuilder sb, boolean quote) {
		return toUri(sb, quote, null);
	}

	/**
	 * 使用URI参数按指定编码拼接表单项，例：a=123&b=777&c=P%23
	 * 
	 * @param sb      可变字串
	 * @param quote   是否使用双引号包裹值，如：a="123"&b="777"&c="P%23"
	 * @param charset 字符集，UTF-8或GBK，null时为UTF-8
	 * @return 输入的sb
	 */
	public StringBuilder toUri(StringBuilder sb, boolean quote, String charset) {
		if (0 == size()) {
			return sb;
		}
		// 是否用UTF-8
		boolean noUtf8 = (!StringUtil.isEmpty(charset) && !"UTF-8".equalsIgnoreCase(charset));
		Entry entry = m_Entrys.get(0);
		try {
			if (quote) {
				sb.append(entry.key).append("=\"");
				if (noUtf8) {
					sb.append(URLEncoder.encode(entry.value, charset));
				} else {
					UrlUtil.encodeUrl(entry.value, sb);
				}
				sb.append('"');
			} else {
				sb.append(entry.key).append('=');
				if (noUtf8) {
					sb.append(URLEncoder.encode(entry.value, charset));
				} else {
					UrlUtil.encodeUrl(entry.value, sb);
				}
			}
			for (int i = 1; i < m_Entrys.size(); i++) {
				entry = m_Entrys.get(i);
				if (quote) {
					sb.append('&').append(entry.key).append("=\"");
					if (noUtf8) {
						sb.append(URLEncoder.encode(entry.value, charset));
					} else {
						UrlUtil.encodeUrl(entry.value, sb);
					}
					sb.append('\"');
				} else {
					sb.append('&').append(entry.key).append('=');
					if (noUtf8) {
						sb.append(URLEncoder.encode(entry.value, charset));
					} else {
						UrlUtil.encodeUrl(entry.value, sb);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
		return sb;
	}

	/**
	 * 转为JOSN格式串 {"name1":"value1","name2":"value2",...}
	 */
	public String toJson() {
		if (0 == size()) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder(size() * 50);
		Entry e;
		boolean isFirst = true;
		sb.append("{");
		for (int i = 0; i < m_Entrys.size(); i++) {
			e = m_Entrys.get(i);
			if (e.key.length() > 0 && PREFIX == e.key.charAt(0)) {
				// 忽略特殊表单项
				continue;
			}
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(',');
			}
			sb.append("\"").append(e.key).append("\":\"");
			try {
				JsonUtil.escape(e.value, sb);
			} catch (IOException ee) {
				throw new IllegalArgumentException(ee);
			}
			sb.append("\"");
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 使用URI参数编码拼接表单项，例：a=123&b=777&c=P%23
	 * 
	 * @return 格式串
	 */
	public String toUri() {
		if (0 == size()) {
			return "";
		}
		StringBuilder sb = new StringBuilder(size() * 50);
		return toUri(sb, false).toString();
	}

	/**
	 * 使用URI参数编码拼接表单项，且参数值使用双引号裹，例：a="123"&b="777"&c="P%23"
	 * 
	 * @return 格式串
	 */
	public String toUriWithQuote() {
		if (0 == size()) {
			return "";
		}
		StringBuilder sb = new StringBuilder(size() * 50);
		return toUri(sb, true).toString();
	}

	/**
	 * 不编码的直接的URI参数拼接串
	 * 
	 * @return 拼接串
	 */
	public String toLinkString() {
		return toLinkString(null, false).toString();
	}

	/**
	 * 不编码的直接的URI参数拼接串，且参数值使用双引号裹
	 * 
	 * @return 拼接串
	 */
	public String toLinkStringWithQuote() {
		return toLinkString(null, true).toString();
	}

	/**
	 * 不编码的直接的URI参数拼接串
	 * 
	 * @param sb    可变字串，若为null则自动创建
	 * @param quote 是否使用双引号包裹值，如：a="123"&b="777"
	 * @return 输出结果后的可变串
	 */
	public StringBuilder toLinkString(StringBuilder sb, boolean quote) {
		if (null == sb) {
			sb = new StringBuilder(size() * 50);
		}
		if (0 == size()) {
			return sb;
		}
		Entry e;
		boolean isFirst = true;
		for (int i = 0; i < m_Entrys.size(); i++) {
			e = m_Entrys.get(i);
			if (e.key.length() > 0 && PREFIX == e.key.charAt(0)) {
				// 忽略特殊表单项
				continue;
			}
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append('&');
			}
			if (quote) {
				sb.append(e.key).append("=\"").append(e.value).append('"');
			} else {
				sb.append(e.key).append('=').append(e.value);
			}
		}
		return sb;
	}

	/**
	 * 不编码的直接的URI参数拼接串
	 * 
	 * @param sb         可变字串，若为null则自动创建
	 * @param quote      是否使用双引号包裹值，如：a="123"&b="777"
	 * @param ignorenull 是否忽略空则
	 * @return 输出结果后的可变串
	 */
	public StringBuilder toLinkString(StringBuilder sb, boolean quote, boolean ignorenull) {
		if (null == sb) {
			sb = new StringBuilder(size() * 50);
		}
		if (0 == size()) {
			return sb;
		}
		Entry e;
		boolean isFirst = true;
		for (int i = 0; i < m_Entrys.size(); i++) {
			e = m_Entrys.get(i);
			if (e.key.length() > 0 && PREFIX == e.key.charAt(0)) {
				// 忽略特殊表单项
				continue;
			}
			if (ignorenull && StringUtil.isEmpty(e.getValue())) {
				continue;
			}
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append('&');
			}
			if (quote) {
				sb.append(e.key).append("=\"").append(e.value).append('"');
			} else {
				sb.append(e.key).append('=').append(e.value);
			}
		}
		return sb;
	}

	/**
	 * 转换成字符串
	 * 
	 * @param sb         可变字串，若为null则自动创建
	 * @param quote      是否使用双引号包裹值，如：a="123"&b="777"
	 * @param ignorenull 是否忽略空则
	 * @param split      连接符
	 * @return 输出结果后的可变串
	 */
	public StringBuilder toLinkString(StringBuilder sb, boolean quote, boolean ignorenull, char split) {
		if (null == sb) {
			sb = new StringBuilder(size() * 50);
		}
		if (0 == size()) {
			return sb;
		}
		Entry e;
		boolean isFirst = true;
		for (int i = 0; i < m_Entrys.size(); i++) {
			e = m_Entrys.get(i);
			if (e.key.length() > 0 && PREFIX == e.key.charAt(0)) {
				// 忽略特殊表单项
				continue;
			}
			if (ignorenull && StringUtil.isEmpty(e.getValue())) {
				continue;
			}
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(split);
			}
			if (quote) {
				sb.append(e.key).append("=\"").append(e.value).append('"');
			} else {
				sb.append(e.key).append('=').append(e.value);
			}
		}
		return sb;
	}

	/**
	 * 按XML格式输出
	 * 
	 * @param sb 可变字串，若为null则自动创建
	 * @return 输出结果后的可变串
	 */
	public StringBuilder toXml(StringBuilder sb) {
		if (null == sb) {
			sb = new StringBuilder(size() * 50);
		}
		if (0 == size()) {
			return sb;
		}
		Entry e;
		for (int i = 0; i < m_Entrys.size(); i++) {
			e = m_Entrys.get(i);
			if (e.key.length() > 0 && PREFIX == e.key.charAt(0)) {
				// 忽略特殊表单项
				continue;
			}
			sb.append('<').append(e.key).append('>');
			sb.append("<![CDATA[").append(e.value).append("]]>");
			sb.append("</").append(e.key).append('>');
		}
		return sb;
	}

	/**
	 * XML格式输出
	 */
	public String getXml() {
		return toXml(null).toString();
	}

	/**
	 * 表单项
	 * 
	 * @author liangyi
	 * 
	 */
	public static final class Entry implements Map.Entry<String, String> {
		String key;
		String value;

		Entry(Map.Entry<String, String> e) {
			this.key = e.getKey();
			this.value = e.getValue();
		}

		Entry(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		public String setValue(String value) {
			String old = this.value;
			this.value = value;
			return old;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj instanceof String) {
				return equals((String) obj);
			}
			return false;
		}

		public boolean equals(String obj) {
			return key.equalsIgnoreCase((String) obj);
		}
	}

	/**
	 * entrySet枚举实现
	 * 
	 * @author liangyi
	 * 
	 */
	class EntryIterator implements Iterator<Map.Entry<String, String>> {
		int m_Index;

		public boolean hasNext() {
			return (m_Index < size());
		}

		public Map.Entry<String, String> next() {
			return m_Entrys.get(m_Index++);
		}

		public void remove() {
			throw new UnsupportedOperationException("FormMap.Iterator不支持删除操作");
		}
	}
}
