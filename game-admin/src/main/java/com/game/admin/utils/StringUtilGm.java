package com.game.admin.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtilGm {
	/** 系统默认的编码,UTF-8 {@index} */
	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String SLASH = "/";
	// 空字符串。
	public static final String EMPTY_STRING = "";
	//特殊符号
	public static final String REGEX = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	//加号
	public static final String ADD_STRING = "+";
	//减号
	public static final String SUB_STRING = "-";
	
	/**
	 * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。
	 * 
	 * <pre>
	 * StringUtil.isEmpty(null)      = true
	 * StringUtil.isEmpty(&quot;&quot;)        = true
	 * StringUtil.isEmpty(&quot; &quot;)       = false
	 * StringUtil.isEmpty(&quot;bob&quot;)     = false
	 * StringUtil.isEmpty(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * @param str 要检查的字符串
	 * @return 如果为空, 则返回<code>true</code>
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0 ;
	}
	
	/**
	 * 除去字符串头尾部的空白，如果字符串是<code>null</code>，依然返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.trim(null)          = null
	 * StringUtil.trim(&quot;&quot;)            = &quot;&quot;
	 * StringUtil.trim(&quot;     &quot;)       = &quot;&quot;
	 * StringUtil.trim(&quot;abc&quot;)         = &quot;abc&quot;
	 * StringUtil.trim(&quot;    abc    &quot;) = &quot;abc&quot;
	 * </pre>
	 * 
	 * @param str 要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * 除去字符串头尾部的空白，如果结果字符串是空字符串<code>""</code>，则返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.trimToNull(null)          = null
	 * StringUtil.trimToNull(&quot;&quot;)            = null
	 * StringUtil.trimToNull(&quot;     &quot;)       = null
	 * StringUtil.trimToNull(&quot;abc&quot;)         = &quot;abc&quot;
	 * StringUtil.trimToNull(&quot;    abc    &quot;) = &quot;abc&quot;
	 * </pre>
	 * 
	 * @param str 要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
	 *         <code>null</code>
	 */
	public static String trimToNull(String str) {
		if (str == null) {
			return null;
		}

		String result = str.trim();

		if (result == null || result.length() == 0) {
			return null;
		}

		return result;
	}
	/**
	 * 判断此字符串是否为空、空字符串（包括只含空格的字符串）
	 * 
	 * <pre>
	 * StringUtils.isNullStr(null)      = true
	 * StringUtils.isNullStr( )        = true
	 * StringUtils.isNullStr(   )       = true
	 * StringUtils.isNullStr( bob )     = false
	 * StringUtils.isNullStr(   bob   ) = false
	 * </pre>
	 * 
	 * @param str
	 *            待检查的字符串
	 * @return 如果为null或空字符串（包括只含空格的字符串）则返回true，否则返回false
	 */
	public static boolean isNull(String str) {
		return str == null || str.trim().length() == 0;
	}
	/**
	 * 生成UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		return id;
	}

	/**
	 *获取字符串 字节长度(一个中文两个字节)
	 * @param value
	 * @return
	 */
	public static int String_length(String value) {
		int valueLength = 0;
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; ++i) {
			valueLength += (chars[i] > 0xff) ? 2 : 1;
		}
		return valueLength;
	}
	
	/**
	 * 排除非法符号
	 * @param str
	 * @return
	 */
	public static String filterHeroName(String str){
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(str);
		if(m.find()){
			str = m.replaceAll("").trim(); 
		}
		return str.replaceAll(" ", "");
	}
	
	
}
