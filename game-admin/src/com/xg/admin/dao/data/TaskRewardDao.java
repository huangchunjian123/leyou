package com.xg.admin.dao.data;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;
import net.paoding.rose.jade.core.Identity;

import com.xg.admin.pojo.task.TaskRewardData;

/**
 * 任务奖励dao层
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@DAO
public interface TaskRewardDao {

	public final static String TABLE = "t_task_reward";

	@SQL("insert into " + TABLE
			+ " (F_task_id,F_reward_grade,F_reward_goods,F_gold,F_yuanbao,F_contribution,F_title,F_exp,F_notice,F_team_exp)values"
			+ "(:t.taskId,:t.rewardGrade,:t.rewardGoods,:t.gold,:t.yuanbao,:t.contribution,:t.title,:t.exp,:t.notice,:t.teamExp)")
	public Identity add(@SQLParam("t") TaskRewardData t);

	@SQL("update "
			+ TABLE
			+ " set F_task_id =:t.taskId,F_reward_grade=:t.rewardGrade,F_reward_goods=:t.rewardGoods,F_gold=:t.gold,F_yuanbao=:t.yuanbao,F_contribution=:t.contribution,F_title=:t.title"
			+ ",F_exp=:t.exp,F_notice=:t.notice,F_team_exp=:t.teamExp where f_id=:t.id")
	public void update(@SQLParam("t") TaskRewardData t);

	@SQL("select F_id as id,F_task_id  as taskId,F_reward_grade as rewardGrade,F_reward_goods as rewardGoods, F_gold as gold,F_yuanbao as yuanbao,F_contribution as contribution,F_title as title"
			+ ",F_exp as exp,F_notice as notice,F_team_exp as teamExp from " + TABLE + " where F_task_id=:taskId")
	public List<TaskRewardData> get(@SQLParam("taskId") int taskId);

	@SQL("select F_id as id,F_task_id  as taskId,F_reward_grade as rewardGrade,F_reward_goods as rewardGoods,F_gold as gold,F_yuanbao as yuanbao,F_contribution as contribution,F_title as title"
			+ ",F_exp as exp,F_notice as notice,F_team_exp as teamExp from " + TABLE)
	public List<TaskRewardData> getAll();

	@SQL("delete from " + TABLE + " where F_task_id=:taskId")
	public void deleteByTaskId(@SQLParam("taskId") int taskId);

	@SQL("delete from " + TABLE + " where F_id=:rewardId")
	public void deleteByRewardId(@SQLParam("rewardId") int rewardId);
}
