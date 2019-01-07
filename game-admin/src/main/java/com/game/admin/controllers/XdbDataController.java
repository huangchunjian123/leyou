package com.game.admin.controllers;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.python.google.common.collect.Maps;

import com.alibaba.fastjson.JSONObject;
import com.game.admin.utils.Constants;
import com.game.admin.utils.DynamicObjectCreator;
import com.game.admin.utils.HttpclientUtil;
import com.game.admin.utils.PagingUtils;
import com.game.admin.utils.RefObject;
import com.game.admin.utils.ResultCode;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import net.paoding.rose.web.annotation.Param;

/**
 * xdb数据
 * @author huangchunjian
 *
 * 乐游网络科技有限公司
   huangchunjian1741@dingtalk.com
 */
public class XdbDataController extends BaseController {
	private static Map<String,List<String>> cachesQueryColumns = Maps.newConcurrentMap();
	
	/**
	 * goList
	 * @return   
	 * String  
	 * @exception
	 */
	public String goDatas(@Param("surl") String surl,@Param("tablename") String tablename) {
		return "xdb/list";
	}
	
	public Object del(@Param("tablename") String tablename,
			@Param("tablekey") String tablekey,@Param("surl") String surl) {
//		surl = com.game.admin.utils.Escape.unescape(surl);
		String gsonString = null;
		try {
			Map<String,String > map = new LinkedHashMap<>();
			map.put("tablename", tablename);
			map.put("key", tablekey);
			gsonString = HttpclientUtil.post(surl+Constants.TABLE_URL+"/xdbdel"
				,map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		 try {
			 map = gson.fromJson(gsonString, map.getClass());
		} catch (Exception e) {
			return ResultCode.error("解析json出错");
		}
		String result = (String)map.get("result");
		if ("ok".equals(result)) {
			return ResultCode.succ("success");
		} else {
			return ResultCode.succ("fail");
		}
	}
	
	
	/**
	 * 
	 * @Cmd(comment="xdb数据更新")
	public Object updateCell(@Param(name = "tablename", comment = "表名") final String tablename
			,@Param(name = "key", comment = "tablekey") final String key
			,@Param(name = "column", comment = "column") final String column
			,@Param(name = "columnValue", comment = "columnValue") final String columnValue) {
		
	 * 更新某个表的某个字段数值
	 * @param tablename
	 * @param column
	 * @param value
	 * @return
	 */
	public Object updateCell(@Param("tablename") String tablename
			,@Param("changes") String changes,@Param("rowdatas") String rowdatas,
			@Param("surl") String surl) {
//		surl = com.game.admin.utils.Escape.unescape(surl);
		changes = com.game.admin.utils.Escape.unescape(changes);
		rowdatas = com.game.admin.utils.Escape.unescape(rowdatas);
		
		JSONObject jo = JSONObject.parseObject(rowdatas);
		String key = jo.getString("key");
		JSONObject chjo = JSONObject.parseObject(changes);
		String _column ="",_columnValue = "";
		for (Iterator iterator = chjo.keySet().iterator(); iterator.hasNext();) {
			 _column = (String) iterator.next();
			 _columnValue = chjo.getString(_column);
			break;
		}
		String gsonString = HttpclientUtil.get(surl+Constants.TABLE_URL+"/updatecell"
				+ "?tablename=" + tablename
				+ "&key=" + key
				+ "&column=" + _column
				+ "&columnValue=" + _columnValue
				);
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		 Map<String, Map<String,Map<String,String>>> map = new HashMap<String, Map<String,Map<String,String>>>();
       
		map = gson.fromJson(gsonString, map.getClass());
		String result = (String)map.get("result");
		if ("ok".equals(result)) {
			return ResultCode.succ("success");
		} else {
			return ResultCode.succ("fail");
		}
	}
	
	public Object addobject(@Param("tablename") String tablename,
			@Param("dataJson") String dataJson,@Param("surl") String surl) {
//		surl = com.game.admin.utils.Escape.unescape(surl);
		dataJson = com.game.admin.utils.Escape.unescape(dataJson);
		JSONObject chjo = JSONObject.parseObject(dataJson);
		String _column ="",_columnValue = "";
		for (Iterator iterator = chjo.keySet().iterator(); iterator.hasNext();) {
			 String key = (String) iterator.next();
			 String value = chjo.getString(key);
			 _column += key +"#";
			 _columnValue += "".equals(value)?"0_":value +"_";
		}
		if (_column.endsWith("#")) {
			_column = _column.substring(0,_column.length()-1);
		}
		if (_columnValue.endsWith("_")) {
			_columnValue = _columnValue.substring(0,_columnValue.length()-1);
		}
		String gsonString = null;
		try {
			Map<String,String > map = new LinkedHashMap<>();
			map.put("tablename", tablename);
			map.put("columns", _column);
			map.put("columnvalues", URLEncoder.encode(_columnValue,"utf-8"));
			gsonString = HttpclientUtil.post(surl+Constants.TABLE_URL+"/add"
				,map);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		 try {
			 map = gson.fromJson(gsonString, map.getClass());
		} catch (Exception e) {
			return ResultCode.error("解析json出错");
		}
		String result = (String)map.get("result");
		if ("ok".equals(result)) {
			return ResultCode.succ("success");
		} else {
			return ResultCode.succ("fail");
		}
	}
	
	public Object getaddcols(@Param("tablename") String tablename,@Param("surl") String surl) {
		List<String> resulttablecols = cachesQueryColumns.get(surl+tablename);
		if (null == resulttablecols)
			return ResultCode.error("not find cols");
//		System.err.println(ResultCode.writeJson(resulttablecols));
//		System.err.println("cols:"+resulttablecols.size());
		return ResultCode.writeJson(resulttablecols);
	}
	
	/**
	 * 查询数据列
	 * @return
	 */
	public Object getcols(@Param("tablename") String tablename,@Param("surl") String surl) {
//		surl = com.game.admin.utils.Escape.unescape(surl);
		String gsonString = HttpclientUtil.get(surl+Constants.TABLE_URL+"/getbbtablecols?tablename=" + tablename);
		Gson gson = new Gson();
		
		 Map<String, Object> map = new LinkedHashMap<String, Object>();
        
		map = gson.fromJson(gsonString, map.getClass());
		List<String> tablecols = (List<String>)map.get("result");
		List<String> resulttablecols = Lists.newArrayList();
		List<String> cachetablecols = Lists.newArrayList();
		if (tablecols != null && !tablecols.isEmpty()) {
			for (Iterator iterator = tablecols.iterator(); iterator.hasNext();) {
				String column = (String) iterator.next();
				if (column.endsWith("_a")) {
					resulttablecols.add(column.substring(0, column.length()-2));
					cachetablecols.add(column.substring(0, column.length()-2));
				}else {
					resulttablecols.add(column);
				}
			}
		}
//		if (!cachesQueryColumns.containsKey(tablename)) {
			cachesQueryColumns.put(surl+tablename, cachetablecols);
//		}
		System.err.println(ResultCode.writeJson(resulttablecols));
//		System.err.println("cols:"+resulttablecols.size());
		return ResultCode.writeJson(resulttablecols);
	}
	
	/**
	 * 
	 * public Map<Object, Object> getXdbDatas(
			 @Param(name = "fromindex", comment = "从当前索引下标开始") final int fromindex,
			 @Param(name = "count", comment = "查找的数量") final int count,
			 @Param(name = "tablename", comment = "查找的表") final String tablename){
			 
	 * 查询数据
	 * @return
	 */
	public Object datagrid(@Param("page") int page, @Param("rows") int rows, @Param("tablename") String tablename
			,@Param("surl") String surl,@Param("qConditions") String qConditions) {
		if (page < 0){
			page = 1;
		}
		qConditions = com.game.admin.utils.Escape.unescape(qConditions);
		qConditions=Strings.isNullOrEmpty(qConditions)?"0":qConditions.replace("=", "$");
		int pagesize = (rows == 0) ? 20 : rows;
//		surl = com.game.admin.utils.Escape.unescape(surl);
		List<Object> listAll = new ArrayList<Object>();
		int fromindex = (page -1 ) * pagesize;
		String gsonString = HttpclientUtil.get(surl+Constants.TABLE_URL+"/getxdbdatas"
				+ "?tablename=" + tablename
				+ "&fromindex=" + fromindex
				+ "&count=" + pagesize
				+ "&qConditions=" + qConditions
				);
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		 Map<String, Map<String,Map<String,String>>> map = new HashMap<String, Map<String,Map<String,String>>>();
       
		map = gson.fromJson(gsonString, map.getClass());
		
		if (map == null) {
			return ResultCode.error("false");
		}
		Map tablecols = (Map)map.get("result");
//		Map<String,Map<String,String>> tabledatas = map.get("result");
		/**
		 *  final Map<Object, Object> map = new LinkedHashMap<Object, Object>();
		 *  
		 */
		
		int totalsize= 0; 
		for (Iterator iterator = tablecols.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			ArrayList value = (ArrayList)tablecols.get(key);
			DynamicObjectCreator.fillDataList(tablename, listAll, value,new RefObject<Integer>(0));
			totalsize = Integer.parseInt(key);
		}
		
		List<Object> returnlistAll = listAll;
		/*if (!Strings.isNullOrEmpty(qConditions)&& !"0".equals(qConditions)) {
			String[] conditions = qConditions.split("#");
			Map<String,String> condMap= com.google.common.collect.Maps.newHashMap();
			for (int i = 0; i < conditions.length; i++) {
				String condition = conditions[i].split("=")[0];
				String conditionV = conditions[i].split("=")[1];
				condMap.put(condition, conditionV);
			}
			
			for (Iterator iterator = listAll.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				try {
					
					int find = 0;
					for (Iterator iterator2 = condMap.keySet().iterator(); iterator2.hasNext();) {
						String condition = (String) iterator2.next();
						String conditionV = condMap.get(condition);
						Field field = object.getClass().getField(condition);
						if (field != null) {
							Object fieldvalue = field.get(object);
							if (conditionV.equals(fieldvalue.toString())) {
								find ++ ;
							}
						}
					}
					if (find == condMap.size()) {
						returnlistAll.add(object);
					}
				} catch (Exception e) {
					continue;
				}
			}
			totalsize = returnlistAll.size();
		}else {
			returnlistAll = listAll;
		}*/
		System.err.println(ResultCode.writeJson(PagingUtils.getDataGrid(totalsize,page+1, pagesize, returnlistAll)));
//		System.err.println("data:"+listAll.size());
		return ResultCode.writeJson(PagingUtils.getDataGrid(totalsize,page+1, pagesize, returnlistAll));
	}
	
	
}
