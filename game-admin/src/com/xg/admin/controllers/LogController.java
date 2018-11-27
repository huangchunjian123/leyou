package com.xg.admin.controllers;

import org.apache.log4j.Logger;

/**
 * 日志控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class LogController extends BaseController {

	private static final Logger logger = Logger.getLogger(LogController.class);

	/**
	 * 跳转到日志
	 * @return
	 */
	public String goLog() {
		return "log/operatelog";
	}

}
