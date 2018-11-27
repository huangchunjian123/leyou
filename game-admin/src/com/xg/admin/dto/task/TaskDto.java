package com.xg.admin.dto.task;

/**
 * task
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class TaskDto {
	private String taskType;
	private int taskId;
	private String taskName;

	public TaskDto(int id, int type, String name) {
		//0主线任务；1日常任务；2公会任务；3其他任务
		if (type == 0) {
			this.taskType = "主线任务";
		} else if (type == 1) {
			this.taskType = "日常任务";
		} else if (type == 2) {
			this.taskType = "公会任务";
		} else {
			this.taskType = "其他任务";
		}
		this.taskId = id;
		this.taskName = name;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

}
