package com.xg.admin.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.python.antlr.PythonParser.return_stmt_return;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;
import com.xg.admin.dto.task.TaskDto;
import com.xg.admin.pojo.task.TaskConditionData;
import com.xg.admin.pojo.task.TaskData;
import com.xg.admin.pojo.task.TaskRewardData;
import com.xg.admin.service.task.ITaskService;
import com.xg.admin.utils.ResourceUtil;
import com.xg.admin.utils.ResultCode;
import com.xg.game.api.hessian.HeroService;

/**
 * 任务控制器
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class TaskController extends BaseController {

	@Autowired
	private ITaskService taskService;

	/**
	 * 跳转至任务列表页面
	 * goList
	 * @return   
	 * String  
	 * @exception
	 */
	public String goTaskList() {
		return "task/list";
	}

	/**
	 * 刷新内存数据
	 * @return
	 */
	public Object refresh() {
		taskService.initTasks();
		return ResultCode.succ("任务数据刷新成功");
	}

	/**
	 * 跳转至更新缓存界面
	 * @return
	 */
	public String goUpdateCache() {
		return "task/updatecache";
	}

	/**
	 * 获得任务列表
	 */
	public Object getTaskList(@Param("qTaskType") Integer taskType, @Param("qTaskId") Integer taskId,
			@Param("qTaskName") String taskName, @Param("page") int page, @Param("rows") int rows) {
		if (page < 0)
			page = 1;
		taskType = null == taskType ? -1 : taskType;
		taskId = null == taskId ? 0 : taskId;
		taskName = null == taskName ? "" : "%" + taskName + "%";
		int pagesize = rows == 0 ? 10 : rows;
		int offset = (page - 1) * pagesize;
		return ResultCode.writeJson(taskService.datagrid(taskType, taskId, taskName, offset, pagesize));
	}

	/**
	 * 取所有任务下拉列表
	 * 
	 * @param name
	 * @param excludeId
	 * @param page
	 * @param rows
	 */
	public Object getalltasks(@Param("q") String q, @Param("excludeId") Integer excludeId) {
		List<TaskDto> list = taskService.getTaskDtoByname(q, excludeId, 0, 10);
		return ResultCode.writeJson(list);
	}

	/**
	 * 添加任务 TODO
	 * @param taskJson
	 * @param conditionJson
	 * @param rewardJson
	 * @return
	 */
	public Object add(@Param("taskJson") String taskJson, @Param("conditionJson") String conditionJson,
			@Param("rewardJson") String rewardJson) { 
		try {
			TaskData task = JSON.parseObject(taskJson, TaskData.class);
			if (taskService.existsTaskId(task.getTaskId())) {
				return ResultCode.error(String.format("任务[%d]已存在！", task.getTaskId()));
			}
			List<TaskConditionData> conditions = JSON.parseArray(conditionJson, TaskConditionData.class);
			List<TaskRewardData> rewards = JSON.parseArray(rewardJson, TaskRewardData.class);
			taskService.addTask(task, conditions, rewards);
			return ResultCode.succ("添加任务成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultCode.error("添加任务失败！");
	}

	/**
	 * 获取任务数据
	 * @param taskId
	 * @return
	 */
	public Object getTask(@Param("taskId") Integer taskId) {
		TaskData data = taskService.getData(taskId);
		return ResultCode.writeJson(data);
	}

	/**
	 * 获取所有任务条件id
	 * @param taskId
	 * @return
	 */
	public Object getTaskConditionIds(@Param("taskId") Integer taskId) {
		List<Integer> ids = taskService.getConditionIds(taskId);
		if (ids == null) {
			return ResultCode.error(String.format("任务[%d]不存在条件配置！", taskId));
		}
		return ResultCode.writeJson(ids);
	}

	/**
	 * 获取任务条件
	 * @param taskId
	 * @return
	 */
	public Object getTaskCondition(@Param("taskId") Integer taskId, @Param("conditionId") Integer conditionId) {
		return ResultCode.writeJson(taskService.getCondition(taskId, conditionId));
	}

	/**
	 * 获取所有任务奖励id
	 * @param taskId
	 * @return
	 */
	public Object getTaskRewardIds(@Param("taskId") Integer taskId) {
		List<Integer> ids = taskService.getRewardId(taskId);
		if (ids == null) {
			return ResultCode.error(String.format("任务[%d]不存在奖励配置！", taskId));
		}
		return ResultCode.writeJson(ids);
	}

	/**
	 * 获取任务奖励
	 * @param taskId
	 * @return
	 */
	public Object getTaskReward(@Param("taskId") Integer taskId, @Param("rewardId") Integer rewardId) {
		return ResultCode.writeJson(taskService.getReward(taskId, rewardId));
	}

	/**
	 * 更新任务
	 * @param taskJson
	 * @param conditionJson
	 * @param rewardJson
	 * @return
	 */
	public Object update(@Param("taskJson") String taskJson, @Param("conditionJson") String conditionJson,
			@Param("rewardJson") String rewardJson) {
		try {
			TaskData task = JSON.parseObject(taskJson, TaskData.class);
			if (!taskService.existsTaskId(task.getTaskId())) {
				return ResultCode.error(String.format("任务[%d]不存在！", task.getTaskId()));
			}
			if (task.getPreTaskId() != null && task.getPreTaskId() > 0 && task.getTaskId().equals(task.getPreTaskId())) {
				return ResultCode.error(String.format("任务[%d]前置任务不能配置成自己！", task.getTaskId()));
			}
			List<TaskConditionData> conditions = JSON.parseArray(conditionJson, TaskConditionData.class);
			List<TaskRewardData> rewards = JSON.parseArray(rewardJson, TaskRewardData.class);
			taskService.updateTask(task, conditions, rewards);
			return ResultCode.succ("更新任务成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultCode.error("更新任务失败!");
	}

	/**
	 * 删除任务
	 * @param taskId
	 * @return
	 */
	public Object deleteByTaskId(@Param("taskId") Integer taskId) {
		if (!taskService.existsTaskId(taskId)) {
			return ResultCode.error(String.format("任务[%d]不存在！", taskId));
		}
		taskService.deleteTask(taskId);
		return ResultCode.succ("删除任务成功!");
	}
	
	/**
	 * 取闯关下拉列表
	 * 
	 * @param name
	 * @param page
	 * @param rows
	 */
	public Object getMainTaskList(@Param("q") String q) {
		List<TaskDto> list = taskService.getAllMainTasks(q);
		Collections.sort(list, new Comparator<TaskDto>(){

			@Override
			public int compare(TaskDto o1, TaskDto o2) {
				if (o1.getTaskId()<o2.getTaskId()) {
					return -1;
				}else 	if (o1.getTaskId()>o2.getTaskId()){
					return 1;
				}
				return 0;
			}});
		return ResultCode.writeJson(list);
	}
	
	public Object perfectTasks(@Param("pid") int pid, @Param("serverId") int serverId,
			@Param("heroId") String heroId,@Param("mainTaskId") int mainTaskId) {
		serverId = ResourceUtil.getServerId();
		if (null == heroId || "".equals(heroId)||0==mainTaskId) {
			return ResultCode.error("参数错误heroId");
		}
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			String url = buildUrl(serverId, "/game-server/api/service/accounthero");
			try {
				HeroService heroService = (HeroService) factory.create(HeroService.class, url);
				if (null == heroService) {
					logger.error("不存在的api");
					return ResultCode.error("不存在的api");
				} else {
					//(int serverId,int pageSize,int page);
					int result = heroService.perfectTasks(heroId,mainTaskId);
					if (result == 1) {
						logger.error(String.format("账号【%s】不存在", heroId));
						return ResultCode.error(String.format("账号【%s】不存在", heroId));
					}else if (result == 2) {
						logger.error(String.format("参数错误mainTaskId", mainTaskId));
						return ResultCode.error(String.format("参数错误mainTaskId", mainTaskId));
					}
					return ResultCode.succ("操作成功");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error("api出错，操作失败");
	}

	public Object getCurrentTaskId(@Param("pid") int pid, @Param("serverId") int serverId,
	@Param("heroId") String heroId) {
		serverId = ResourceUtil.getServerId();
		if (null == heroId || "".equals(heroId)) {
			return ResultCode.error("参数错误heroId");
		}
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		{
			String url = buildUrl(serverId, "/game-server/api/service/accounthero");
			try {
				HeroService heroService = (HeroService) factory.create(HeroService.class, url);
				if (null == heroService) {
					logger.error("不存在的api");
					return ResultCode.error("不存在的api");
				} else {
					//(int serverId,int pageSize,int page);
					int taskId = heroService.getTaskId(heroId);
					return ResultCode.writeJson(new TaskDto(taskId,0,taskService.getData(taskId).getTaskName()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResultCode.error("api出错，操作失败");
	}

}
