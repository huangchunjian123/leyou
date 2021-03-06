package com.game.admin.dto.model;

import java.util.List;

/**
 * easyui使用的tree模型
 * @author huangchunjian 
 *
 */
@SuppressWarnings("serial")
public class ServerTreeNode implements java.io.Serializable {
	/** 服务器id */
	private String id;
	/** 树节点名称 */
	private String text;
	/** 子节点 */
	private List<ServerTreeNode> children;
	/** 是否展开(open,closed) */
	private String state = "open";

	public ServerTreeNode(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ServerTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ServerTreeNode> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "ServerTreeNode [id=" + id + ", text=" + text + ", children=" + children + ", state=" + state + "]";
	}

}
