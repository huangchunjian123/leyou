package com.game.admin.controllers;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
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
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import net.paoding.rose.web.annotation.Param;

/**
 * xdbmap
 * @author huangchunjian
 *
 * 乐游网络科技有限公司
   huangchunjian1741@dingtalk.com
 */
public class XdbMapDataController {

	
	private static Map<String,List<String>> cachesQueryColumns = Maps.newConcurrentMap();
	public String mapDatas() {
		return "xdb/map";
	}
	
	public Object delmap(
			@Param("fieldnames") String fieldnames,
			@Param("fieldkeys") String fieldkeys,
			@Param("delkey") String delkey,
			@Param("surl") String surl) {
		fieldkeys = com.game.admin.utils.Escape.unescape(fieldkeys);
		delkey = com.game.admin.utils.Escape.unescape(delkey);
//		surl = com.game.admin.utils.Escape.unescape(surl);
		String gsonString = null;
		try {
			Map<String,String > map = new LinkedHashMap<>();
			map.put("fieldnames", fieldnames);
			map.put("fieldkeys", URLEncoder.encode(fieldkeys,"utf-8"));
			map.put("delkey", URLEncoder.encode(delkey,"utf-8"));
			gsonString = HttpclientUtil.post(surl+Constants.MAP_URL+"/delmap"
				,map);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		 Map<String, Map<String,Map<String,String>>> map = new HashMap<String, Map<String,Map<String,String>>>();
       
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
	public Object updateMapCell(
			@Param("changes") String changes,@Param("rowdatas") String rowdatas,
			@Param("surl") String surl) {
			changes = com.game.admin.utils.Escape.unescape(changes);
			rowdatas = com.game.admin.utils.Escape.unescape(rowdatas);
//			surl = com.game.admin.utils.Escape.unescape(surl);
		JSONObject jo = JSONObject.parseObject(rowdatas);
		String keys = jo.getString("key");
		JSONObject chjo = JSONObject.parseObject(changes);
		String _column ="",_columnValue = "";
		for (Iterator iterator = chjo.keySet().iterator(); iterator.hasNext();) {
			 _column = (String) iterator.next();
			 _columnValue = chjo.getString(_column);
			break;
		}
		String gsonString = null;
		try {
			Map<String,String > map = new LinkedHashMap<>();
			map.put("keys", keys);
			map.put("columns", _column);
			map.put("columnValue", URLEncoder.encode(_columnValue,"utf-8"));
			gsonString = HttpclientUtil.post(surl+Constants.MAP_URL+"/updatemapcell"
				,map);
		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		 Map<String, Map<String,Map<String,String>>> map = new HashMap<String, Map<String,Map<String,String>>>();
       
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
	 * fieldnames:fieldnames,
				fieldkeys:fieldkeys,
				dataJson:$.toJSON($('#fmadd').serializeObject())
	 * @return
	 */
	public Object addmapobject(@Param("fieldnames") String fieldnames,
			@Param("fieldkeys") String fieldkeys,
			@Param("dataJson") String dataJson,
			@Param("surl") String surl) {
//		surl = com.game.admin.utils.Escape.unescape(surl);
		fieldkeys = com.game.admin.utils.Escape.unescape(fieldkeys);
		dataJson = com.game.admin.utils.Escape.unescape(dataJson);
		JSONObject chjo = JSONObject.parseObject(dataJson);
		String _column ="",_columnValue = "";
		for (Iterator iterator = chjo.keySet().iterator(); iterator.hasNext();) {
			 String key = (String) iterator.next();
			 String value = chjo.getString(key);
			 _column += key +"#";
			 _columnValue += value +"_";
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
			map.put("fieldnames", fieldnames);
			map.put("fieldkeys", fieldkeys);
			map.put("columns", _column);
			map.put("columnvalues", URLEncoder.encode(_columnValue,"utf-8"));
			gsonString = HttpclientUtil.post(surl+Constants.MAP_URL+"/addmap"
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
	
	public Object getaddmapcols(@Param("fieldnames") String fieldnames) {
		List<String> resulttablecols = cachesQueryColumns.get(fieldnames);
//		System.err.println(ResultCode.writeJson(resulttablecols));
//		System.err.println("cols:"+resulttablecols.size());
		return ResultCode.writeJson(resulttablecols);
	}
	
	//roleskill$equipskillpositions
	public Object getmapcols(@Param("fieldnames") String fieldnames,@Param("surl") String surl) {
		String gsonString = HttpclientUtil.get(surl+Constants.MAP_URL+"/getmaptablecols?fieldnames=" + fieldnames);
		Gson gson = new Gson();
		 Map<String, Object> map = new LinkedHashMap<String, Object>();
        
		 try {
			 map = gson.fromJson(gsonString, map.getClass());
		} catch (Exception e) {
			return ResultCode.error("解析json出错");
		}
//		 surl = com.game.admin.utils.Escape.unescape(surl);
		if (map.get("result") instanceof String) {
			return ResultCode.error((String)map.get("result"));
		}
		List<String> tablecols = (List<String>)map.get("result");
		List<String> resulttablecols = Lists.newArrayList();
		List<String> cachetablecols = Lists.newArrayList();
		if (tablecols != null && !tablecols.isEmpty()) {
			for (Iterator iterator = tablecols.iterator(); iterator.hasNext();) {
				String column = (String) iterator.next();
				if (column.endsWith("_a")) {
					cachetablecols.add(column.substring(0, column.length()-2));
					resulttablecols.add(column.substring(0, column.length()-2));
				}else {
					resulttablecols.add(column);
				}
			}
		}
		if (!cachesQueryColumns.containsKey(fieldnames)) {
			cachesQueryColumns.put(fieldnames, cachetablecols);
		}
//		System.err.println(ResultCode.writeJson(resulttablecols));
//		System.err.println("cols:"+resulttablecols.size());
		String first = resulttablecols.remove(0);
		resulttablecols.add(first);
		System.err.println(ResultCode.writeJson(resulttablecols));
//		Field[] files= Heroinfos.class.getDeclaredFields();
//		resulttablecols.clear();
//		for (int i = 0; i < files.length; i++) {
//			resulttablecols.add(files[i].getName());
//		}
		return ResultCode.writeJson(resulttablecols);
	}
	
	public Object mapdatagrid(@Param("page") int page, @Param("rows") int rows,@Param("fieldnames") String fieldnames,
			@Param("fieldkeys") String fieldkeys,@Param("surl") String surl) {
		if (page < 0){
			page = 1;
		}
		int pagesize = (rows == 0) ? 20 : rows;
//		surl = com.game.admin.utils.Escape.unescape(surl);
		List<Object> listAll = new ArrayList<Object>();
		int fromindex = (page -1 ) * pagesize;
		String gsonString = HttpclientUtil.get(surl+Constants.MAP_URL+"/getmapxdbdatas"
				+ "?fieldnames=" + fieldnames
				+ "&fieldkeys=" + fieldkeys
				+ "&fromindex=" + fromindex
				+ "&count=" + pagesize
				);
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		 Map<String, Map<String,Map<String,String>>> map = new HashMap<String, Map<String,Map<String,String>>>();
       
		try {
			map = gson.fromJson(gsonString, map.getClass());
		} catch (Exception e) {
			return ResultCode.error("err");
		}
		if (map == null) {
			return ResultCode.error("err");
		}
		if (map.get("result") instanceof String) {
			
			return ResultCode.error(map.get("result").toString());
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
			DynamicObjectCreator.fillDataList(fieldnames, listAll, value,new RefObject<Integer>(0));
			totalsize = Integer.parseInt(key);
		}
//		System.err.println("data:"+listAll.size());
//		System.err.println(ResultCode.writeJson(PagingUtils.getDataGrid(totalsize,page+1, pagesize, listAll)));
//		return ResultCode.writeJson(PagingUtils.getDataGrid(totalsize,page+1, pagesize, listAll));
//		Heroinfos hi1 = new Heroinfos();
//		hi1.setHasunlock_key("1");
//		hi1.setKey("3434");
//		hi1.setOrg_indeger_key("202020");
//		Heroinfos hi21 = new Heroinfos();
//		hi21.setHasunlock_key("2");
//		hi21.setKey("34342");
//		hi21.setOrg_indeger_key("20202011");
//		listAll.clear();
//		listAll.add(hi1);
//		listAll.add(hi21);
		System.err.println(ResultCode.writeJson(listAll));
		return ResultCode.writeJson(listAll);
	}
}
