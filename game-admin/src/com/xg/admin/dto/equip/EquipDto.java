package com.xg.admin.dto.equip;

/**
 * 装备
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class EquipDto {
	private int id;
	private String name;

	public EquipDto(int id, String name) {
		this.id = id;
		this.name = name;
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
