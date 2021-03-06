package com.game.admin.dto.model;

/**
 * 定义常用的gm发的道具id
 * @author huangchunjian 
 *
 */
public enum NormalGood {
	GOLD(90002,"钻石","gold"), 
	COPPER(90001, "金币","copper");

	private int goodId;
	private String name;
	private String value;
	
	NormalGood(int goodId, String name, String value) {
		this.goodId = goodId;
		this.name = name;
		this.value = value;
	}

	public int getGoodId() {
		return goodId;
	}



	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public static NormalGood getById(int goodId){
		NormalGood[] gains = NormalGood.values();
		for(NormalGood ng : gains){
			if(ng.getGoodId() == goodId){
				return ng;
			}
		}
		return null;
	}
	public static NormalGood getByValue(String value){
		NormalGood[] gains = NormalGood.values();
		for(NormalGood ng : gains){
			if(ng.getValue().equals(value)){
				return ng;
			}
		}
		return null;
	}

	
}
