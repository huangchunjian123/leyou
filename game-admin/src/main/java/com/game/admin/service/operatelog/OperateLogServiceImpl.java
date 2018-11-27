package com.game.admin.service.operatelog;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.v6.Lists;

import com.game.admin.dao.system.OperateLogDataDao;
import com.game.admin.pojo.system.OperateLogData;
import com.game.admin.utils.DateUtils;
import com.game.admin.utils.PagingUtils;
import com.game.admin.utils.StringUtilGm;
import com.game.api.hessian.DataGrid;
/**
 * 操作日志实现
 * @author huangchunjian
 *
 */
@Service("operateLogService")
public class OperateLogServiceImpl implements IOperateLogService, InitializingBean{
	
	@Autowired
	private OperateLogDataDao logDao;
	
	private static final Map<Integer,OperateLogData> logmap =new ConcurrentHashMap<Integer,OperateLogData>();
	
	@Override
	public OperateLogData get(int id) {
		OperateLogData logData = logmap.get(id);
		if(null == logData){
			logData = logDao.get(id);
			if(null == logData){
				return null;
			}
		}
		return logData;
	}
	
	@Override
	public DataGrid datagrid(int page, int rows) {//当前页  页数记录数
		List<OperateLogData> listAll = Lists.newArrayList();
		for(Entry<Integer, OperateLogData> en : logmap.entrySet()){
			listAll.add(en.getValue());
		}
		//list进行排序
		Collections.sort(listAll, logTimeComparator);
		return PagingUtils.getDataGrid(page, rows, listAll);
	}

	@Override
	public void add(String operator, String event, String status, Date logTime) {
		OperateLogData data = new OperateLogData();
		data.setOperator(operator);
		data.setEvent(event);
		data.setStatus(status);
		data.setLogTime(logTime);
		System.out.println(data.toString());
		int oid = logDao.save(data).intValue();
		data.setId(oid);
		logmap.put(oid, data);
	}

	@Override
	public void delete(int id) {
		OperateLogData logData= get(id);
		if(logData != null){
			logmap.remove(id);
			logDao.delete(id);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<OperateLogData> list = logDao.getAll();
		for (OperateLogData log :list) {
			logmap.put(log.getId(), log);
		}
	}
	
	
	private static final Comparator<OperateLogData> logTimeComparator = new Comparator<OperateLogData>(){
		@Override
		public int compare(OperateLogData o1, OperateLogData o2) {
			return o2.getLogTime().compareTo(o1.getLogTime());
		}
	};
	
	
	/**
	 * 模糊查询(list中做，sql语句无法匹配)
	 */
	public List<OperateLogData> listLikeQuery(List<OperateLogData> Listall,String operatorsName, String startTime, String endTime, String keyword) {
		Pattern patternO;Pattern patternK;
		List<OperateLogData> logLikeDatas = Lists.newArrayList();
		//写个公共的查询方法直接调用
		if(StringUtilGm.isEmpty(startTime) && StringUtilGm.isEmpty(endTime) && StringUtilGm.isEmpty(operatorsName) && StringUtilGm.isEmpty(keyword)){
			return Listall;
		}else if (!StringUtilGm.isEmpty(startTime) && !StringUtilGm.isEmpty(endTime)) {
			//字符串转化date类型
			Date startDate= DateUtils.getDateByString(startTime);
			Date endDate  = DateUtils.getDateByString(endTime);
			if(!operatorsName.equals("1") &&  !StringUtilGm.isEmpty(keyword)){
				patternO = Pattern.compile(operatorsName,Pattern.CASE_INSENSITIVE);
				patternK = Pattern.compile(keyword,Pattern.CASE_INSENSITIVE);
				for (OperateLogData logData : Listall) {
					Date logDate = logData.getLogTime();
					if(logDate.after(startDate) && logDate.before(endDate)){
						OperateLogData data = getCommonLog(patternO, patternK, logData, operatorsName, keyword);
						if(data !=  null ){
							logLikeDatas.add(data);
						}
					}
				}	
			}else if(!StringUtilGm.isEmpty(keyword) && operatorsName.equals("1")){
				patternK = Pattern.compile(keyword,Pattern.CASE_INSENSITIVE);
				for (OperateLogData operateLogData : Listall) {
					Date logDate = operateLogData.getLogTime();
					if(logDate.after(startDate) && logDate.before(endDate)){
						OperateLogData data = getCommonLogK(patternK, operateLogData, keyword);
						if(data != null ){
							logLikeDatas.add(data);
						}
					}
				}
			}else if (!operatorsName.equals("1") && StringUtilGm.isEmpty(keyword)) {
				patternO = Pattern.compile(operatorsName,Pattern.CASE_INSENSITIVE);
				for (OperateLogData operateLogData : Listall) {
					Date logDate = operateLogData.getLogTime();
					if(logDate.after(startDate) && logDate.before(endDate)){
						OperateLogData data = getCommonLogO(patternO, operateLogData, operatorsName);
						if(data != null ){
							logLikeDatas.add(data);
						}
					}
				}
			}else {
				return getSubTimeList(Listall, startDate, endDate);
			}
		}else if(!operatorsName.equals("1") &&  !StringUtilGm.isEmpty(keyword)){
				patternO = Pattern.compile(operatorsName,Pattern.CASE_INSENSITIVE);
				patternK = Pattern.compile(keyword,Pattern.CASE_INSENSITIVE);
				for (OperateLogData operateLogData : Listall) {
					OperateLogData data = getCommonLog(patternO, patternK, operateLogData, operatorsName, keyword);
					if(data != null){
						logLikeDatas.add(data);
					}
				}
		}else if (!StringUtilGm.isEmpty(keyword) && operatorsName.equals("1")) {
				patternK = Pattern.compile(keyword,Pattern.CASE_INSENSITIVE);
				for (OperateLogData operateLogData : Listall) {
					OperateLogData data = getCommonLogK(patternK, operateLogData, keyword);
					if(data != null ){
						logLikeDatas.add(data);
					}
				}
		}else if (!operatorsName.equals("1") && StringUtilGm.isEmpty(keyword)) {
			patternO = Pattern.compile(operatorsName,Pattern.CASE_INSENSITIVE);
			for (OperateLogData operateLogData : Listall) {
				OperateLogData data = getCommonLogO(patternO, operateLogData, operatorsName);
				if(data != null ){
					logLikeDatas.add(data);
				}
			}
		}else {
			logLikeDatas = Listall;
		}
		return logLikeDatas;
	}
	
	
	
	/**
	 * 带参数的总模糊查询 
	 */
	@Override
	public DataGrid paramsQuery(int page, int rows, String operatorsName, String startTime, String endTime, String keyword) {
		List<OperateLogData> listAll = Lists.newArrayList();
		for(Entry<Integer, OperateLogData> en : logmap.entrySet()){
			listAll.add(en.getValue());
		}
		List<OperateLogData> logLikeDatas= listLikeQuery(listAll, operatorsName, startTime, endTime, keyword);
		//list进行排序
		Collections.sort(logLikeDatas, logTimeComparator);
		return PagingUtils.getDataGrid(page, rows, logLikeDatas);
	}
	
	/**
	 * 只是时间段的日志数据
	 * @param Listall
	 * @param date1
	 * @param date2
	 * @return
	 */
	public List<OperateLogData> getSubTimeList(List<OperateLogData> Listall ,Date date1 , Date date2){
		List<OperateLogData> timeList = Lists.newArrayList();
		for (OperateLogData logData : Listall) {
			Date logDate = logData.getLogTime();
			if(logDate.after(date1) && logDate.before(date2)){
				timeList.add(logData);
			}
		}
		return  timeList;
	}
	/**
	 * 操作者和关键字模糊查询
	 * @param Listall
	 * @param operatorsName
	 * @param keyword
	 * @return
	 */
	public List<OperateLogData> getKeyAndOperatorLike(List<OperateLogData> Listall,
			String operatorsName ,String keyword){
		List<OperateLogData> logLikeDatas = Lists.newArrayList();
		Pattern	patternO = Pattern.compile(operatorsName,Pattern.CASE_INSENSITIVE);
		Pattern patternK = Pattern.compile(keyword,Pattern.CASE_INSENSITIVE);
		for (OperateLogData logData : Listall) {
			if(!operatorsName.equals("1") &&  !StringUtilGm.isEmpty(keyword)){
				Matcher matcherO = patternO.matcher(logData.getOperator());
				Matcher matcherK = patternK.matcher(logData.getEvent());
				if(matcherO.find() && matcherK.find()){
					logLikeDatas.add(logData); 
				}
			}else if (!StringUtilGm.isEmpty(keyword) && operatorsName.equals("1")) {
				Matcher matcherK = patternK.matcher(logData.getEvent());
				if(matcherK.find()){
					logLikeDatas.add(logData);
				}
			}else { //=-1
				Matcher matcherO = patternO.matcher(logData.getOperator());
				if(matcherO.find()){
					logLikeDatas.add(logData);
				}
			}
		}
		return logLikeDatas;
	}
	
	/**
	 * 获取普通操作者和关键字
	 * @param patternO
	 * @param patternK
	 * @param logData
	 * @param operatorsName
	 * @param keyword
	 * @return
	 */
	public OperateLogData getCommonLog(Pattern patternO,Pattern patternK,OperateLogData logData,String operatorsName,String keyword){
		if(!operatorsName.equals("1") &&  !StringUtilGm.isEmpty(keyword)){
			Matcher matcherO = patternO.matcher(logData.getOperator());
			Matcher matcherK = patternK.matcher(logData.getEvent());
			if(matcherO.find() && matcherK.find()){
				return logData;
			}
		}else if (!StringUtilGm.isEmpty(keyword) && operatorsName.equals("1")) {
			Matcher matcherK = patternK.matcher(logData.getEvent());
			if(matcherK.find()){
				return logData;
			}
		}else { //=-1
			Matcher matcherO = patternO.matcher(logData.getOperator());
			if(matcherO.find()){
				return logData;
			}
		}
		return null;
	}
	
	
	/**
	 * 获取关键字
	 * @param pattern
	 * @param logData
	 * @param strK
	 * @return
	 */
	public OperateLogData getCommonLogK(Pattern pattern,OperateLogData logData,String strK){
		if(!StringUtilGm.isEmpty(strK)){
			Matcher matcher = pattern.matcher(logData.getEvent());
			if(matcher.find()){
					return logData;
			}
		}
		return  null;
	}
	
	/**
	 * 获取操作者
	 * @param pattern
	 * @param logData
	 * @param strO
	 * @return
	 */
	public OperateLogData getCommonLogO(Pattern pattern,OperateLogData logData,String strO){
		if(!strO.equals("1")){
			Matcher matcher = pattern.matcher(logData.getOperator());
			if(matcher.find()){
					return logData;
			}
		}
		return  null;
	}
	
}
