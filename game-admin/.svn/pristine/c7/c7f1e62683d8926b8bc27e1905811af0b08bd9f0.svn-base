package com.xg.admin.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import net.paoding.rose.web.InvocationLocal;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xg.admin.dto.server.Result;
import com.xg.admin.pojo.server.ServerData;
import com.xg.admin.service.server.IServerService;
import com.xg.admin.utils.DateUtils;
import com.xg.admin.utils.ExceptionUtil;
import com.xg.admin.utils.MD5Util;
import com.xg.admin.utils.ResourceUtil;
import com.xg.admin.utils.URLToStringBuilder;

/**
 * 控制器基类
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class BaseController {

	protected static final Logger logger = Logger.getLogger(BaseController.class);

	@Resource
	protected InvocationLocal inv;

	@Autowired
	protected IServerService serverService;

	protected String buildUrl(int serverId, String uri) {
		ServerData serverData = serverService.getByServerId(serverId);
		if (null == serverData){
			return "";
		}
		String url = "http://" + serverData.getServerIp() + ":" + serverData.getServerPort() + uri;
		//		String url = "http://192.168.1.131:8080" + uri;
		Properties properties = new Properties();
		properties.setProperty("datetime", DateUtils.FormatFullDate(DateUtils.getNowDate()));
		String content = URLToStringBuilder.toString(properties);
		String digest = MD5Util.md5(content + ResourceUtil.getPrivateKey());
		properties.put("sign", digest);
		return url + "?" + URLToStringBuilder.toString(properties, "UTF-8");
	}

	protected String getRetMessage(String name, List<Result> resultList) {
		StringBuffer sb = new StringBuffer();
		for (Result sr : resultList) {
			if (sr.getRet() == 1) {
				sb.append("<span style='color:red'>").append(sr.getName()).append(":").append(sr.getMsg())
						.append("</span><br/>");
			}
		}
		if (StringUtils.isNotBlank(sb.toString())) {
			return name + "失败<br/>" + sb.toString();
		}
		return name + "成功";
	}

	//概括性的message
	protected String getRetSimpleMessage(String name, List<Result> resultList) {
		StringBuffer sb = new StringBuffer();
		StringBuffer failServerStr = new StringBuffer();
		for (Result sr : resultList) {
			if (sr.getRet() == 1) {
				failServerStr.append(sr.getName()).append(",");
				//				sb.append("<span style='color:red'>").append(sr.getName()).append(":").append(sr.getMsg()).append("</span><br/>");
			}
		}
		if (StringUtils.isNotBlank(failServerStr)) {
			sb.append("<span style='color:red'>").append(failServerStr).append("Failed").append("</span><br/>");
		}
		if (StringUtils.isNotBlank(sb.toString())) {
			return name + "失败<br/>" + sb.toString();
		}
		return name + "成功";
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @throws IOException
	 */
	public void writeJson(Object object) {
		try {
			String json = JSON
					.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
			//System.out.println(json);
			inv.getResponse().setContentType("text/html;charset=utf-8");
			inv.getResponse().getWriter().write(json);
			inv.getResponse().getWriter().flush();
		} catch (IOException e) {
			logger.debug(ExceptionUtil.getExceptionMessage(e));
		}
	}

}
