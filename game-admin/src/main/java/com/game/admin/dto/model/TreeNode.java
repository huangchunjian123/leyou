package com.game.admin.dto.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.game.admin.pojo.system.MenuData;

/**
 * easyui使用的tree模型
 * @author huangchunjian 
 *
 */
public class TreeNode implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7829593288945210963L;
	private int id;
	private String text;// 树节点名称
	//	private String iconCls;// 前面的小图标样式
	private boolean checked = false;// 是否勾选状态
	private Map<String, Object> attributes;// 其他参数
	private List<TreeNode> children;// 子节点
	private String state = "open";// 是否展开(open,closed)

	public TreeNode() {
		Map<String, Object> attributes = new HashMap<String, Object>();
		this.attributes = attributes;
	}

	public TreeNode(MenuData tm) {
		this.id = tm.getId();
		this.text = tm.getName();
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("url", tm.getUrl());
		this.attributes = attributes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
