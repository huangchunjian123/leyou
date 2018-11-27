package com.game.admin.utils;

import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

/**
 * 参数工具类
 * @author huangchunjian 
 *
 */
public final class ResourceUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	/**
	 * 是否是测试模式
	 * @return
	 */
	public static final boolean checkIsDebug() {
		String str = bundle.getString("isdebug");
		if (str.equals("true")) {
			return true;
		}
		return false;
	}

	public static final String getResource(String key) {
		String str = bundle.getString(key);
		return str;
	}

	public static final String getPrivateKey() {
		return getResource("privateKey");
	}
	
	public static final int getServerId() {
		  if (StringUtils.isEmpty(getResource("serverId"))){
			  return 0;
		}else {
			return Integer.parseInt(getResource("serverId"));
		}
	}
}
