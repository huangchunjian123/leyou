package com.xg.admin.dto.good;

public class GoodsReward {
	private String id;
	private String name;
	private int num;
	
	public GoodsReward(String id, String name,int num) {
		this.id = id;
		this.name = name;
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}