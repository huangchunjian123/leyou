package com.game.admin.eventbus;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.game.admin.dto.model.SessionInfo;
import com.game.admin.eventbus.type.UserEvent;
import com.game.admin.service.operatelog.IOperateLogService;
import com.google.common.eventbus.Subscribe;

@Component
public class LogEvents extends AbstractEventHandler {
	
	@Autowired
	private IOperateLogService LogService;
	
	/***
	 * 每个的操作触发的时候 记录日志
	 */
	@Subscribe
	public void WriteLog(UserEvent event){
		String status;
		
		SessionInfo info = event.getBody();
		String operator= info.getRealName();
		Date date =new Date();
		String content= event.getContent();
		int isSucc = event.getIsSucc();
		if(isSucc == 1){
			status = "操作成功";
		}else {
			status = "操作失败";
		}
		LogService.add(operator, content, status, date);
	}
	
}
