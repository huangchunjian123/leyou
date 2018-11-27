/**
 *
 */
package com.system.common;

/**
 * @author y.z
 */
public class Const {
	// 错误代码
	public static final Integer SYS_ERROR_CODE = -1; //系统错误
	public static final Integer ISBLANK_ERROR_CODE = -2; //必传参数不能为空
	public static final Integer ADMIN_ERROR_CODE = -3; //用户登录过期
	public static final Integer OTHER_ERROR_CODE = -4; //自定义错误码
	public static final Integer AUTH_EROOR_CODE = -5; //自定义错误码
	public static final Integer SUCCESS_CODE = 0;//成功码

	//错误消息
	public static final String SYS_ERROR_MSG = "系统错误";
	public static final String ISBLANK_ERROR_MSG = "必传参数不能为空";
	public static final String ADMIN_ERROR_MSG = "用户登录过期";
	public static final String SUCCESS_MSG = "数据获取成功";
	public static final String AUTH_EROOR_MSG = "没有限权";
	public static final String UPDATE_SUCCESS_MSG = "修改成功";
	public static final String ADD_SUCCESS_MSG = "添加成功";
	public static final String DEL_SUCCESS_MSG = "删除成功";

	public static final int PAGE_SIZE = 10;
}
