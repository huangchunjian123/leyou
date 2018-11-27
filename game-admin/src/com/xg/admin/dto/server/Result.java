package com.xg.admin.dto.server;


/**
 * gm调用gameserver结果
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class Result {
	/** 返回状态码 */
	private int ret;
	/** 返回的提示消息 */
	private String msg;
	/** 返回的对象 */
	private Object retobj;
	/** 服务器名 */
	private String name;

	private Result(int ret, String msg, Object retobj, String name) {
		this.ret = ret;
		this.msg = msg;
		this.retobj = retobj;
		this.name = name;
	}

	public static Result errorResult(String msg, String name) {
		return new Result(1, msg, null, name);
	}

//	public static Result retResult(MsgResponse response, String name) {
//		return new Result(response.getCode(), response.getBufferAsString(), null, name);
//	}

	public static Result retErrObject(String msg, String name) {
		return new Result(1, msg, null, name);
	}

	public static Result retSuccObject(Object obj) {
		return new Result(0, "", obj, "");
	}

	public static Result retSuccObject() {
		return new Result(0, "", null, "");
	}

	public boolean isSuccess() {
		return this.ret == 0 ? true : false;
	}

	public int getRet() {
		return ret;
	}

	public String getMsg() {
		return msg;
	}

	public Object getRetobj() {
		return retobj;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return this.ret + "|" + this.name + "|" + this.msg;
	}
}
