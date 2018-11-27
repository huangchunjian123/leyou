package com.game.admin.eventbus.event;


/**
 * 事件基类
 * T存放user
 *
 */
public class Event<T> implements IEvent<T> {

	/** 消息体 */
	private final T body;

	public Event(T body) {
		this.body = body;
	}

	@Override
	public T getBody() {
		return body;
	}
}
