package com.xg.admin.utils;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 响应结果
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class ResultCode {
	
	/**
	 * 请求成功返回
	 */
	public static final int SUCC = 0;
	/**
	 * 请求失败返回
	 */
	public static final int FAILT = 1;
	
	/**
	 * 创建成功请求结果
	 * @param map
	 * @param message
	 * @return
	 */
	public static JSONObject error(String message){
		JSONObject json=new JSONObject();
		json.put("status",FAILT);
		json.put("msg", message);
		return JSONObject.fromObject(json.toString());
	}
	
	public static JSONObject succ(String message){
		JSONObject j=new JSONObject();
		j.put("status",SUCC);
		j.put("msg", message);
		return JSONObject.fromObject(j.toString());
    }
	
	public static Object writeJson(Object object) {
//		return JSONObject.fromObject(object);
		String str = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
//		System.out.println(str);
		return JSON.parse(str);
		//String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
		//return json;
	}
	
	public static void main(String[] args) {
		JSONObject j=new JSONObject();
		j.put("r",SUCC);	
		j.put("m", 0);
		System.out.println(JSONObject.fromObject(j.toString()));
	}
}
