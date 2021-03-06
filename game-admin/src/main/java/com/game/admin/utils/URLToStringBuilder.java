package com.game.admin.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

/**
 * 内容ToString构建器
 * 
 * <p>
 * 本构建器主要针对内容按key排序生成key1=value1&key2=value2键值对的字符串。
 * </p>
 * @author huangchunjian 
 */
public abstract class URLToStringBuilder {

	/** & 间隔key-value对 */
	public static final String AND = "&";

	/** = 间隔key value*/
	public static final char EQ = '=';

	private URLToStringBuilder() {
	}

	/**
	 * 针对content中的内容，根据key排序，然后组成url形式的字符串
	 * <p>
	 * <ol>
	 * <li>null == content： 返回null</li>
	 * <li>对content中的key进行字典序排序，然后生成按照key排序后的键值对形成的字符串</li>
	 * </ol>
	 * 
	 * 例子:
	 * <pre>
	 * { {"bkey", "bvalue"},
	 *   {"dkey", "cvalue"},
	 *   {"akey", "avalue"},
	 *   {"ckey", "cvalue"},
	 * }
	 * 
	 * ==>
	 * akey=avalue&bkey=bvalue&ckey=cvalue&dkey=dvalue
	 * </pre>
	 * 
	 * @param prop 内容
	 * @param encoding 编码
	 * @return url形式的字符串
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String toString(Properties prop, String encoding) {
		if (null == prop) {
			return null;
		}

		StringBuffer buf = new StringBuffer();

		List<String> keys = new ArrayList(prop.keySet());

		Collections.sort(keys);

		for (int i = 0, size = keys.size(); i < size; i++) {
			String key = (String) keys.get(i);
			String value = prop.getProperty(key);
			try {
				value = java.net.URLEncoder.encode(value, encoding);
			} catch (UnsupportedEncodingException ex) {
			}

			buf.append((0 == i ? "" : AND) + key + EQ + value);
		}

		return buf.toString();
	}

	/**
	 * 将text转换为properties
	 * <p>
	 * <ol>
	 * <li>空的text, 空的prop</li>
	 * <li>将text转化后的prop</li>
	 * </ol>
	 * 
	 * <p>
	 * 例子:
	 * 
	 * bkey=bvalue&ckey=cvalue&dkey=dvalue&akey=avalue
	 * 
	 * ==>
	 * 
	 * {
	 *  dkey=dvalue, 
	 *  ckey=cvalue, 
	 *  bkey=bvalue, 
	 *  akey=avalue
	 * }
	 * 
	 * @param text 文本，形式为key=value&key2=value2
	 * @param encoding 特定编码
	 * @return properties
	 */
	public static Properties toProp(String text, String encoding) {

		Properties prop = new Properties();
		if (StringUtils.isEmpty(text)) {
			return prop;
		}

		StringTokenizer st = new StringTokenizer(text, AND);
		while (st.hasMoreTokens()) {
			String entry = st.nextToken();
			int i = entry.indexOf(EQ);

			if (i < 0) {
				return prop;
			}

			String value = entry.substring(i + 1);
			if (!StringUtils.isEmpty(value)) {
				try {
					value = java.net.URLDecoder.decode(value, encoding);
				} catch (UnsupportedEncodingException ex) {
				}
			}
			prop.put(entry.substring(0, i), value);
		}
		return prop;
	}

	/**
	 * 将text转换为properties，默认编码
	 * @param text 文本，形式为key=value&key2=value2
	 * @return properties
	 */
	public static Properties toProp(String text) {
		return toProp(text,"UTF-8");
	}

	/**
	 * 针对content中的内容，根据key排序，然后组成url形式的字符串，使用缺省编码
	 * @param prop 内容
	 * @return url形式的字符串
	 */
	public static String toString(Properties prop) {
		return toString(prop,"UTF-8");
	}
}
