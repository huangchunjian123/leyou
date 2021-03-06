package com.game.admin.eventbus.type;



import org.springframework.beans.factory.annotation.Autowired;

import com.game.admin.dto.model.SessionInfo;
import com.game.admin.eventbus.event.Event;
import com.game.admin.service.operatelog.IOperateLogService;

/**
 * GM用户操作的时候
 * @author huangchunjian
 *
 */
public class UserEvent extends Event<SessionInfo>{
	
	/**用户操作用户封装日志**/
	private String  content;
	/**是否成功*/
	private int isSucc;

	public UserEvent(SessionInfo body) {
		super(body);
	}
	@Autowired
	private IOperateLogService LogService;
	
	public static UserEvent valueOf(SessionInfo info,String  content,int isSucc){
		UserEvent event = new UserEvent(info);
		event.content = content;
		event.isSucc = isSucc;
		return event;
	}

	
	public int getIsSucc() {
		return isSucc;
	}


	public void setIsSucc(int isSucc) {
		this.isSucc = isSucc;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
