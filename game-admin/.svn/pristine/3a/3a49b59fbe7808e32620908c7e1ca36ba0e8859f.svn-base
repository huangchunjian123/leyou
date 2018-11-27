package com.game.admin.dto.model;

import java.util.List;

/**
 * sessionInfo模型
 */
public class SessionInfo implements java.io.Serializable {

	private int userId;// 用户ID
	private String realName;// 用户真名
	private String loginName;// 用户登录名称
	private String ip;// IP地址

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return loginName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public boolean isYunYing() {
		return "梁帅".equals(this.loginName) || "彭景林".equals(this.loginName) || "邓江华".equals(this.loginName);
	}
}
