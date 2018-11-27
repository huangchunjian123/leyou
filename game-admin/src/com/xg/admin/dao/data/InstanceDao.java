package com.xg.admin.dao.data;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.xg.admin.pojo.InstanceData;

/**
 * 副本dao层
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@DAO
public interface InstanceDao {

	public final static String TABLE = "t_instance";

	@SQL("select F_id as id,F_name as name,F_rewardgrade as rewardgrade,f_monster_list as monsterList from " + TABLE)
	public List<InstanceData> getAll();
	@SQL("select t_instance.F_id as id,t_instance.F_name as name,t_instance.F_rewardgrade as rewardgrade,t_instance.f_monster_list  as monsterList  from t_instance LEFT JOIN t_map ON t_instance.F_map_id= t_map.F_id WHERE t_map.F_map_type=1;")
	public List<InstanceData> getAllJuQing();
}
