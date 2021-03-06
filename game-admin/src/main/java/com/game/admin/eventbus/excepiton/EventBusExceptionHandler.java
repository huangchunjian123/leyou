package com.game.admin.eventbus.excepiton;

import org.slf4j.Logger;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
/**
 * 事件异常处理
 * @author huangchunjian
 *
 */
public class EventBusExceptionHandler implements SubscriberExceptionHandler{

	private String name;
	
	public static  Logger eventLog ;
	
	
	public EventBusExceptionHandler(String name) {
		this.name = name;
	}
	@Override
	public void handleException(Throwable exception, SubscriberExceptionContext context) {
		eventLog.error(
				String.format("%s > Could not dispatch event: %s to %s", name, context.getSubscriber(),
						context.getSubscriberMethod()), exception);
	}

}
