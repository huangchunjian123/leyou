package com.game.admin.pojo.system;

import java.util.Date;

/**
 * 用户数据
 * @author huangchunjian 
 *
 */
public class UserData implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6461070483925019519L;
	private int id;
	private Date createDateTime;
	private Date modifyDateTime;
	private String name;
	private String pwd;
	private String roleId;
	private String roleNames;
	private String mail;
	private String mobile;
	private String realName;
	private String status;
	private String subCompany;
	private String userType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getModifyDateTime() {
		return modifyDateTime;
	}

	public void setModifyDateTime(Date modifyDateTime) {
		this.modifyDateTime = modifyDateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubCompany() {
		return subCompany;
	}

	public void setSubCompany(String subCompany) {
		this.subCompany = subCompany;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void deleteRole(int roleId) {
		StringBuffer sb = new StringBuffer();
		String[] ids = this.roleId.split(",");
		for (String id : ids) {
			if (!id.equals(roleId + "")) {
				sb.append(id).append(",");
			}
		}
		String ls = sb.toString();
		if (ls.contains(",")) {
			ls = ls.substring(0, ls.length() - 1);
		}
		setRoleId(ls);
		setRoleNames("");
	}

	public boolean checkRole(int roleId) {
		String[] ids = this.roleId.split(",");
		for (String id : ids) {
			if (id.equals(roleId + "")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Tuser [id=" + id + ", name=" + name + ", pwd=" + pwd + ", roleId=" + roleId + "]";
	}

}