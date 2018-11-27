package com.xg.admin.pojo.system;

import org.apache.commons.lang3.StringUtils;

/**
 * 角色数据
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class RoleData implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 434266266422562106L;
	private int id;
	private String roleName;
	private String roleDesc;
	private String authId = "";

	public RoleData() {
	}

	public RoleData(String desc, String name) {
		this.roleDesc = desc;
		this.roleName = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public void deleteAuth(int menuId) {
		if (StringUtils.isNotEmpty(this.authId)) {
			StringBuffer sb = new StringBuffer();
			String[] ids = this.authId.split(",");
			for (String id : ids) {
				if (!id.equals(menuId + "")) {
					sb.append(id).append(",");
				}
			}
			String ls = sb.toString();
			if (ls.contains(",")) {
				ls = ls.substring(0, ls.length() - 1);
			}
			this.authId = ls;
		}
	}

	public void addAuth(int menuId) {
		this.authId = authId + "," + menuId;
	}

	public boolean checkAuth(int menuId) {
		if (StringUtils.isNotEmpty(this.authId)) {
			String[] ids = this.authId.split(",");
			for (String id : ids) {
				if (id.equals(menuId + "")) {
					return true;
				}
			}
		}
		return false;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}
}