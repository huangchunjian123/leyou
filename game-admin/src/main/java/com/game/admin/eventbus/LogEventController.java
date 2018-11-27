package com.game.admin.eventbus;



import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import com.game.admin.eventbus.event.IEvent;
import com.game.admin.eventbus.excepiton.EventBusExceptionHandler;
import com.game.admin.utils.ApplicationContextHolderGm;
import com.google.common.eventbus.EventBus;
/**
 * 事件总处理器
 * @author huangchunjian
 *
 */
@Component
public class LogEventController implements ApplicationListener<ContextClosedEvent> {
	
	/** 日志 */
	private static final Logger eventLog = EventBusExceptionHandler.eventLog;
	
	/** 单线程同步事件消费 */
	public final EventBus EVENT_MANAGE;
	
	/** 是否关闭服务 */
	private volatile boolean stop;
	
	public LogEventController() {
		EVENT_MANAGE = new EventBus(new EventBusExceptionHandler("同步事件总线"));
	}
	
    public final static LogEventController getInstance(){
    	return ApplicationContextHolderGm.getBean(LogEventController.class);
    }
	
    
	/**触发事件处理***/
	public  void post(IEvent<?> event){
		if (null == event) {
			throw new IllegalArgumentException("事件对象不能为空");
		}
		EVENT_MANAGE.post(event);
	}
	
	/**注册事件*/
	public void register(AbstractEventHandler handler) {
		if (handler == null) {
			throw new IllegalArgumentException("事件接收处理器不能为空");
		}
		EVENT_MANAGE.register(handler);
	}
	
	/**
	 * 从事件总线中取消注册监听者
	 */
	public void unregister(AbstractEventHandler handler) {
		if (handler == null) {
			throw new IllegalArgumentException("事件接收处理器不能为空");
		}
		EVENT_MANAGE.unregister(handler);
	}
	
	public boolean isStop() {
		return this.stop;
	}
	
	/**
	 * 停服
	 */
	public void shutdown() {
		if (isStop()) {
			return;
		}
		this.stop = Boolean.TRUE;
	}
	
	@Override
	public void onApplicationEvent(ContextClosedEvent arg0) {
		// TODO Auto-generated method stub
	}
	
}
