package com.xg.admin.dao.data;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;
import net.paoding.rose.jade.core.Identity;

import com.xg.admin.pojo.task.TaskData;

/**
 * 任务dao层
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@DAO
public interface TaskDao {

	public final static String TABLE = "t_task";

	@SQL("insert into "
			+ TABLE
			+ " (F_task_id,F_task_name,F_type,F_group_type,f_pre_task_id,F_is_cancel,f_is_repeat,f_daily_count,F_begin_npc,F_end_npc,f_trigger_goods,f_limit_time,f_one_key_finish_consume_yuanbao,f_accept_begin_time,f_complete_end_time,F_task_desc,f_min_grade,f_max_grade)values"
			+ "(:t.taskId,:t.taskName,:t.type,:t.groupType,:t.preTaskId,:t.isCancel,:t.isRepeat,:t.dailyCount,:t.beginNpc,:t.endNpc,:t.triggerGoods,:t.limitTime,:t.oneKeyFinishConsumeYuanbao,:t.acceptBeginTime,:t.completeEndTime,:t.taskDesc,:t.minGrade,:t.maxGrade)")
	public Identity add(@SQLParam("t") TaskData t);

	@SQL("update "
			+ TABLE
			+ " set F_task_id =:t.taskId ,F_task_name=:t.taskName,F_type=:t.type,F_group_type=:t.groupType,f_pre_task_id=:t.preTaskId,F_is_cancel=:t.isCancel,f_is_repeat=:t.isRepeat,f_daily_count=:t.dailyCount,F_begin_npc=:t.beginNpc,F_end_npc=:t.endNpc,f_trigger_goods=:t.triggerGoods,"
			+ "f_limit_time=:t.limitTime,f_one_key_finish_consume_yuanbao=:t.oneKeyFinishConsumeYuanbao,f_accept_begin_time=:t.acceptBeginTime,f_complete_end_time=:t.completeEndTime,F_task_desc=:t.taskDesc,f_min_grade=:t.minGrade,f_max_grade=:t.maxGrade  where f_id=:t.id")
	public void update(@SQLParam("t") TaskData t);

	@SQL("select F_id as id,F_task_id as taskId,F_task_name as taskName,F_type as type,F_group_type as groupType,f_min_grade as minGrade,f_max_grade as maxGrade,"
			+ "f_pre_task_id as preTaskId,F_is_cancel as isCancel,f_is_repeat as isRepeat,f_daily_count as dailyCount,F_begin_npc as beginNpc,F_end_npc as endNpc,"
			+ "f_trigger_goods as triggerGoods,f_limit_time as limitTime,f_one_key_finish_consume_yuanbao as oneKeyFinishConsumeYuanbao,f_accept_begin_time as acceptBeginTime,"
			+ "f_complete_end_time as completeEndTime,F_task_desc as taskDesc from " + TABLE
			+ " where F_task_id=:taskId")
	public TaskData get(@SQLParam("taskId") int taskId);

	@SQL("select F_id as id,F_task_id as taskId,F_task_name as taskName,F_type as type,F_group_type as groupType,f_min_grade as minGrade,f_max_grade as maxGrade,"
			+ "f_pre_task_id as preTaskId,F_is_cancel as isCancel,f_is_repeat as isRepeat,f_daily_count as dailyCount,F_begin_npc as beginNpc,F_end_npc as endNpc,"
			+ "f_trigger_goods as triggerGoods,f_limit_time as limitTime,f_one_key_finish_consume_yuanbao as oneKeyFinishConsumeYuanbao,f_accept_begin_time as acceptBeginTime,"
			+ "f_complete_end_time as completeEndTime,F_task_desc as taskDesc from " + TABLE
			+ " where 1=1 #if(:taskName != ''){ and F_task_name like :taskName} "
			+ " #if(:taskId >0){ and F_task_id = :taskId} " + " #if(:type >= 0){ and F_type = :type} order by F_type,F_task_id"
			+ " limit :offset,:pagesize")
	public List<TaskData> findParams(@SQLParam("taskId") int taskId, @SQLParam("taskName") String taskName,
			@SQLParam("type") int type, @SQLParam("offset") int offset, @SQLParam("pagesize") int pagesize);

	@SQL("select count(*) from " + TABLE + " where 1=1 #if(:taskName != ''){ and F_task_name like :taskName} "
			+ " #if(:type >= 0){ and F_type = :type} #if(:taskId >0){ and F_task_id = :taskId} ")
	public int findParamsCount(@SQLParam("taskId") int taskId, @SQLParam("taskName") String taskName,
			@SQLParam("type") int type);

	@SQL("select F_id as id,F_task_id as taskId,F_task_name as taskName,F_type as type,F_group_type as groupType,f_min_grade as minGrade,f_max_grade as maxGrade,"
			+ "f_pre_task_id as preTaskId,F_is_cancel as isCancel,f_is_repeat as isRepeat,f_daily_count as dailyCount,F_begin_npc as beginNpc,F_end_npc as endNpc,"
			+ "f_trigger_goods as triggerGoods,f_limit_time as limitTime,f_one_key_finish_consume_yuanbao as oneKeyFinishConsumeYuanbao,f_accept_begin_time as acceptBeginTime,"
			+ "f_complete_end_time as completeEndTime,F_task_desc as taskDesc from " + TABLE
			+ " where 1=1 #if(:type >= 0){ and F_type = :type} order by F_task_id")
	public List<TaskData> getAll(@SQLParam("type") int type);

	@SQL("delete from " + TABLE + " where F_task_id=:taskId")
	public void delete(@SQLParam("taskId") int taskId);

}
