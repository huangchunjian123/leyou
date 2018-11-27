package com.xg.admin.dto.good;

import com.xg.admin.dto.model.NormalGood;
import com.xg.admin.pojo.Goods;

/**
 * 道具
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class GoodsDto {
	private int id;
	private String name;

	public GoodsDto(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public GoodsDto(NormalGood normalGood) {
		this.id = normalGood.getGoodId();
		this.name = normalGood.getName();
	}
	
	public GoodsDto (Goods good){
		this.id = good.getGoodsId();
		this.name = good.getName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
