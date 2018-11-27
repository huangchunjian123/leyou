package com.xg.admin.dto.skill;

import com.xg.admin.pojo.Skills;

/**
 * 技能
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class SkillsDto {
	private int id;
	private String name;

	public SkillsDto(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	public SkillsDto (Skills skill){
		this.id = skill.getSkillsId();
		this.name = skill.getName();
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
