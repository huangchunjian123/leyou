package com.xg.admin.pojo;

import java.io.Serializable;

/**
 * 技能对象
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class Skills implements Serializable{
	
	private Integer skillsId;//
	private String name;//物品名称
	private int skilltype;
	private int job;
	private int islevelup;
	
	
	
	public int getIslevelup() {
		return islevelup;
	}
	public void setIslevelup(int islevelup) {
		this.islevelup = islevelup;
	}
	public int getSkilltype() {
		return skilltype;
	}
	public void setSkilltype(int skilltype) {
		this.skilltype = skilltype;
	}
	public int getJob() {
		return job;
	}
	public void setJob(int job) {
		this.job = job;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSkillsId() {
		return skillsId;
	}
	public void setSkillsId(Integer skillsId) {
		this.skillsId = skillsId;
	}
	
	
}
