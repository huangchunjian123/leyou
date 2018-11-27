package com.xg.admin.dao.data;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;
import net.paoding.rose.jade.core.Identity;

import com.xg.admin.pojo.task.TaskConditionData;

/**
 * 任务条件dao层
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@DAO
public interface TaskConditionDao {

	public final static String TABLE = "t_task_condition";

	@SQL("insert into "
			+ TABLE
			+ " (F_task_id,f_difficulty_level,f_job,f_target_type,f_target_kill_monster,f_target_goods,f_target_shop,f_target_talk_npc,"
			+ "f_target_need_equip,f_target_action,f_target_min_grade,f_target_instance_id,f_give_buff_id,f_give_goods,f_take_copper,f_trans,F_target_desc,F_entrance_id,F_instance)values"
			+ "(:t.taskId,:t.difficultyLevel,:t.job,:t.targetType,:t.targetKillMonster,:t.targetGoods,:t.targetShop,:t.targetTalkNpc,"
			+ ":t.targetNeedEquip,:t.targetAction,:t.targetMinGrade,:t.targetInstanceId,:t.giveBuffId,:t.giveGoods,:t.takeCopper,:t.trans,:t.targetDesc,:t.entranceId,:t.instance)")
	public Identity add(@SQLParam("t") TaskConditionData t);

	@SQL("update "
			+ TABLE
			+ " set F_task_id =:t.taskId,f_difficulty_level=:t.difficultyLevel,f_job=:t.job,f_target_type=:t.targetType"
			+ ",f_target_kill_monster=:t.targetKillMonster,f_target_goods=:t.targetGoods,f_target_shop=:t.targetShop,f_target_talk_npc=:t.targetTalkNpc,f_target_need_equip=:t.targetNeedEquip,"
			+ "f_target_action=:t.targetAction,f_target_min_grade=:t.targetMinGrade,f_target_instance_id=:t.targetInstanceId,f_give_buff_id=:t.giveBuffId,f_give_goods=:t.giveGoods,"
			+ "f_take_copper=:t.takeCopper,f_trans=:t.trans,F_target_desc=:t.targetDesc,F_entrance_id=:t.entranceId,F_instance=:t.instance where f_id=:t.id")
	public void update(@SQLParam("t") TaskConditionData t);

	@SQL("select F_id as id,F_task_id as taskId,f_difficulty_level as difficultyLevel,f_job as job,f_target_type as targetType"
			+ ",f_target_kill_monster as targetKillMonster,f_target_goods as targetGoods,f_target_shop as targetShop,f_target_talk_npc as targetTalkNpc,f_target_need_equip as targetNeedEquip,"
			+ "f_target_action as targetAction,f_target_min_grade as targetMinGrade,f_target_instance_id as targetInstanceId,f_give_buff_id as giveBuffId,f_give_goods as giveGoods,"
			+ "f_take_copper as takeCopper,f_trans as trans,F_target_desc as targetDesc,F_entrance_id as entranceId,F_instance as instance "
			+ "from "
			+ TABLE
			+ " where F_task_id=:taskId")
	public List<TaskConditionData> get(@SQLParam("taskId") int taskId);

	@SQL("select F_id as id,F_task_id as taskId,f_difficulty_level as difficultyLevel,f_job as job,f_target_type as targetType"
			+ ",f_target_kill_monster as targetKillMonster,f_target_goods as targetGoods,f_target_shop as targetShop,f_target_talk_npc as targetTalkNpc,f_target_need_equip as targetNeedEquip,"
			+ "f_target_action as targetAction,f_target_min_grade as targetMinGrade,f_target_instance_id as targetInstanceId,f_give_buff_id as giveBuffId,f_give_goods as giveGoods,"
			+ "f_take_copper as takeCopper,f_trans as trans,F_target_desc as targetDesc,F_entrance_id as entranceId,F_instance as instance " + "from " + TABLE)
	public List<TaskConditionData> getAll();

	@SQL("delete from " + TABLE + " where F_task_id=:taskId")
	public void deleteByTaskId(@SQLParam("taskId") int taskId);
	
	@SQL("delete from " + TABLE + " where F_id=:conditionId")
	public void deleteByConditionId(@SQLParam("conditionId") int conditionId);

}
