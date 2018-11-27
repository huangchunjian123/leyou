package com.xg.admin.pojo;

import java.io.Serializable;

/**
 * 道具对象
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class Goods implements Serializable{
	
	private Integer goodsId;//
	private String name;//物品名称
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
