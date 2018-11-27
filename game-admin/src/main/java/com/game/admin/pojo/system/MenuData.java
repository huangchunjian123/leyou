package com.game.admin.pojo.system;

/**
 * 菜单数据
 * @author huangchunjian 
 *
 */
public class MenuData implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1801582792110660445L;
	public static final String menu = "01";
	public static final String auth = "02";
	private int id;
	private int pid;
	private String name;
	private int sort;
	private String url;
	/** 权限类型 01:菜单 02:按钮 */
	private String type;

	public MenuData() {
	}

	public MenuData(int pid, String name, int sort, String type, String url) {
		this.pid = pid;
		this.name = name;
		this.sort = sort;
		this.type = type;
		this.url = url;
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static String getMenu() {
		return menu;
	}

	public static String getAuth() {
		return auth;
	}

}