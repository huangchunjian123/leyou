package com.xg.admin.dao.data;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.xg.admin.pojo.Skills;

/**
 * 普通道具dao层
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@DAO
public interface SkillsDao {

	public final static String TABLE = "t_skill";

	@SQL("select F_id as skillsId,F_name as name,F_skill_type as skilltype,F_skilltype as job,F_can_levelup as islevelup from " + TABLE)
	public List<Skills> getAll();

}
