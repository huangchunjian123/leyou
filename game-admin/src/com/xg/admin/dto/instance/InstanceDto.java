package com.xg.admin.dto.instance;


/**
 * 副本dto
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class InstanceDto {

	//columns START
	/**
	 * 副本编号       db_column: F_id 第1个
	 */
	private java.lang.Integer id;
	/**
	 * 副本名称       db_column: F_name 第2个
	 */
	private java.lang.String name;
	/**
	 * 关卡星级（1.普通2.精英3.困难）       db_column: F_rewardgrade 第13个
	 */
	private java.lang.Integer rewardgrade;

	public InstanceDto(int id, String name, int rewardgrade) {
		this.id = id;
		this.name = name;
		this.rewardgrade = rewardgrade;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.Integer getRewardgrade() {
		return rewardgrade;
	}

	public void setRewardgrade(java.lang.Integer rewardgrade) {
		this.rewardgrade = rewardgrade;
	}

}
