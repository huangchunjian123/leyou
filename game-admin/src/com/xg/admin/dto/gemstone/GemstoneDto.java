package com.xg.admin.dto.gemstone;

/**
 * 寶石
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class GemstoneDto {
	private int id;
	private String name;

	public GemstoneDto(int id, String name) {
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
