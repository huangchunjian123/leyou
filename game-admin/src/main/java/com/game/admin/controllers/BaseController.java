package com.game.admin.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.caucho.hessian.client.HessianProxyFactory;
import com.game.admin.dto.model.SessionInfo;
import com.game.admin.eventbus.LogEventController;
import com.game.admin.eventbus.type.UserEvent;
import com.game.admin.utils.ApplicationContextHolderGm;
import com.game.admin.utils.Constants;
import com.game.admin.utils.DateUtils;
import com.game.admin.utils.ExceptionUtil;
import com.game.admin.utils.MD5Util;
import com.game.admin.utils.ResourceUtil;
import com.game.admin.utils.URLToStringBuilder;

import net.paoding.rose.web.InvocationLocal;
/**
 * 控制器基类
 * @author huangchunjian
 *
   huangchunjian1741@dingtalk.com
 */
public class BaseController {

	protected static final Logger logger = Logger.getLogger(BaseController.class);

	@Resource
	protected InvocationLocal inv;


	private Object lock = new Object();
	
	private static final String hessianNmae ="/game-server/api/service/"; 
	
	/**
	 * 
	 * @param serverId 服务器ID  -2代表本地服务器
	 * @param uri /game-server(项目)/api包/service（不变）/accounthero(hession注解配置)
	 * @return
	 */
	protected String buildUrl(int serverId, String uri) {
		String url=""; 
		if(serverId== -2){  
			 url = "http://127.0.0.1:8080" + uri;
		}else{
//			ServerData serverData = serverService.getByServerId(serverId);
//			if (null == serverData){
//				return "";
//			}else{
//				 url = "http://" + serverData.getServerIp() + ":" + serverData.getServerPort() + uri;
//			}
		}	
		Properties properties = new Properties();
		properties.setProperty("datetime", DateUtils.FormatFullDate(DateUtils.getNowDate()));
		String content = URLToStringBuilder.toString(properties);
		String digest = MD5Util.md5(content + ResourceUtil.getPrivateKey());
		properties.put("sign", digest);
		return url + "?" + URLToStringBuilder.toString(properties, "UTF-8");
	}
	
	protected String buildUrl(int serverId) {
		if(serverId == -2) {
			return "http://127.0.0.1:8080" + hessianNmae;
		}
		
		return "";
//		ServerData serverData = serverService.getByServerId(serverId);
//		if(null == serverData) {
//			return StringUtils.EMPTY;
//		}
//		return  "http://" + serverData.getServerIp() + ":" + serverData.getServerPort()+hessianNmae;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getHessianFactory(Class<?> klass,String url) {
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			factory.setPassword("xgkjmmo626");
			factory.setUser("mmo");
			String subStr = klass.getName().substring(klass.getName().lastIndexOf(".")+1);
			if(subStr.equals("AccountService")) {
				subStr = "AccountGmService";
			}
			String result = url+subStr.substring(0, 1).toLowerCase().concat(subStr.substring(1));
			return (T)factory.create(klass,result);
		} catch (MalformedURLException e) {
			logger.info("create HessianFactory fail:"+url.substring(url.lastIndexOf("/")+1));
		}
		return null;
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
	
	protected SessionInfo getSessionInfo(){
		SessionInfo sessioninfo = (SessionInfo)inv.getRequest().getSession().getAttribute(Constants.SESSIONKEY);
		if(sessioninfo != null){
			return sessioninfo;
		}
		return null;
	}
	
	/**
	 * 记录用户操作日志
	 */
	protected void recordOperateLog(String log,int isSucc) {
		synchronized(lock){
			ApplicationContextHolderGm.getBean(LogEventController.class).post(UserEvent.valueOf(getSessionInfo(), log,isSucc));
		}	
	}
	
	public String getByIdServerName(int serverId){
//		ServerData serverData = serverService.getByServerId(serverId);
//		if(!ObjectUtil.isEmptyObject(serverData)){
//			return serverData.getServerName();
//		}else {
			return "";
//		}
	}
	
}
