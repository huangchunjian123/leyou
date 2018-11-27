package com.xg.admin.controllers;

import javax.servlet.http.HttpSession;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xg.admin.controllers.interceptor.LoginRequired;
import com.xg.admin.dto.model.SessionInfo;
import com.xg.admin.pojo.system.UserData;
import com.xg.admin.service.user.IUserService;
import com.xg.admin.utils.Constants;
import com.xg.admin.utils.IpUtil;
import com.xg.admin.utils.ResultCode;

/**
 * 用户登录、退出等操作控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@Path("/")
public class LoginController extends BaseController {

	private static final Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private IUserService userService;

	/**
	 * 转向用户登录页面
	 */
	@LoginRequired
	public String gologin() {
		//显示webapp/views/login.jsp
		return "login";
	}

	/**
	 * 转向后台管理主界面
	 */
	public String gomain() {
		//显示webapp/views/main.jsp
		return "main";
	}

	/**
	 * 处理用户登录系统请求
	 * @param name
	 * @param pwd
	 * @return
	 */
	@LoginRequired
	public Object login(@NotBlank @Param("cname") String name, @NotBlank @Param("cpwd") String pwd) {
		UserData u = userService.login(name, pwd);
		if (u == null) {
			return ResultCode.error("用户名或密码错误!");
		}
		return ResultCode.succ("登录成功!");
	}

	/**
	 * 登录成功将用户信息保存到session中
	 * @param u
	 * @return
	 */
	private SessionInfo saveSessionInfo(UserData user) {
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserId(user.getId());
		sessionInfo.setLoginName(user.getName());
		sessionInfo.setRealName(user.getRealName());
		sessionInfo.setIp(IpUtil.getIpAddr(inv.getRequest()));
		inv.getRequest().getSession().setAttribute(Constants.SESSIONKEY, sessionInfo);
		return sessionInfo;
	}

	/**
	 * 退出系统
	 * @return
	 */
	public Object logout() {
		HttpSession session = inv.getRequest().getSession();
		if (session != null) {
			session.invalidate();
		}
		return ResultCode.succ("注销成功");
	}
}
