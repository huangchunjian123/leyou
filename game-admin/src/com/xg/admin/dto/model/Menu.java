package com.xg.admin.dto.model;

import java.util.List;

import com.xg.admin.pojo.system.MenuData;
/**
 * 菜单
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class Menu implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8082142509866407795L;
	private int id;
	private int pid;
	private String name;
	private String url;
	private int sort;
	private String type;
	private String typeName;
	/**权限类型 01:菜单 02:按钮*/
	private List<Menu> children;

	public Menu(MenuData t) {
		this.id = t.getId();
		this.pid = t.getPid();
		this.name = t.getName();
		this.url = t.getUrl();
		this.sort = t.getSort();
		this.type = t.getType();
		this.typeName = type.equals("01") ? "菜单" : "按钮";
	}

	public Menu(MenuData t, String auths) {
		this.id = t.getId();
		this.pid = t.getPid();
		this.name = t.getName();
		this.url = t.getUrl();
		this.sort = t.getSort();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", pid=" + pid + ", name=" + name + ", url=" + url + ", sort=" + sort + ", children="
				+ children + "]";
	}

}