package com.system.service.impl;

import com.aliyun.odps.data.Record;
import com.system.ExchangeLogFormmat;
import com.system.bean.Log;
import com.system.common.Const;
import com.system.common.util.*;
import com.system.dao.MaxComputeUtil;
import com.system.service.ILogService;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LogServiceImpl implements ILogService {

    @Autowired
    private MaxComputeUtil maxComputeUtil;

    @Autowired
    private EHCacheUtils ehCacheUtils;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");


    private boolean whereFlag = false;
    private String event = " where ";

    @Override
    public String findAll(int page, int pageSize, String service, String startDate, String endDate, String roleId, String roleName, String userId, String account, Integer logType, Integer eventType, String sort) {
        Page<Log> pageData = null;
        String cacheKey = service+startDate+endDate+roleId+roleName+userId+account+logType+eventType;
        Map<String,Object> cacheData = (Map<String,Object>) ehCacheUtils.getCache(cacheKey);
        int startIndex = (page - 1) * pageSize;
        if(null != cacheData){
            List<Log> logList = (List<Log>)cacheData.get("list");
            int totalRow = (int)cacheData.get("totalRow");
            int totalPage = totalRow%pageSize == 0 ? totalRow/pageSize : totalRow/pageSize+1;
            int endIndex = page * pageSize > totalRow?totalRow:page * pageSize;

            pageData = new Page<>(logList.subList(startIndex,endIndex),page,pageSize,totalPage,totalRow);
        }else{
            Page<Log> tempDate = this.getData(service, startDate, endDate, roleId, roleName, userId, account,logType, eventType);
            int totalPage = tempDate.getTotalRow()%pageSize == 0 ? tempDate.getTotalRow()/pageSize : tempDate.getTotalRow()/pageSize+1;
            int endIndex = page * pageSize > tempDate.getTotalRow()?tempDate.getTotalRow():page * pageSize;
            List<Log> logList = tempDate.getList();
            cacheData = new HashMap<>();
            cacheData.put("totalRow",tempDate.getTotalRow());
            cacheData.put("list",logList);
            ehCacheUtils.setCache(cacheKey,cacheData);
            pageData = new Page<>(logList.size() > 0?logList.subList(startIndex,endIndex):logList, page, pageSize, totalPage, tempDate.getTotalRow());
        }
        
        

        return Ret.msgSuccess(pageData);
    }



    private Page<Log> getData(String service, String startDate, String endDate, String roleId, String roleName, String userId, String account, Integer logType, Integer eventType){
        Page<Log> pageData = null;
        StringBuffer sql = new StringBuffer("select l.* from ");
        StringBuffer tableSql = new StringBuffer("");
        StringBuffer params = new StringBuffer();

        try{
            if(StrUtil.isEmpty(startDate) && StrUtil.isEmpty(endDate)){
                List<String> tables = null;

                if(!StrUtil.isBlank(service)){
                    tables = maxComputeUtil.findTables("%_"+service+"_");
                }else {
                    tables = maxComputeUtil.findTables(null);
                }

                for(int i = 0; i < tables.size(); i++){
                    if(i == 0){
                        tableSql.append(" (select * from `"+tables.get(i)+"`");
                        if(null != logType){
                            params.append(whereSql()).append( "log_type = "+logType);
                        }

                        if(null != eventType){
                            params.append(whereSql()).append( " event_type = "+eventType);
                        }
                        restWhere();
                    }else{
                        tableSql.append(" union all select * from `"+tables.get(i)+"` ");
                    }

                    tableSql.append(params);

                }



            }else if(!StrUtil.isEmpty(startDate) && !StrUtil.isEmpty(endDate)){
                int day = DateUtil.getIntervalDays(DateUtil.stringToDate(endDate,"yyyy-MM-dd"),DateUtil.stringToDate(startDate,"yyyy-MM-dd"));

                boolean selectFlag = true;

                for(int i=0;i<=day;i++){
                    Date date = DateUtil.dateAdd(3,i,DateUtil.stringToDate(startDate,"yyyy-MM-dd"));
                    Object [] keys = null;

                    List<String> tables = null;

                    if(!StrUtil.isEmpty(service)){
                        tables = maxComputeUtil.findTables("_"+service+"_"+simpleDateFormat.format(date));
                    }else {
                        tables = maxComputeUtil.findTables(""+simpleDateFormat.format(date));
                    }

                    for(int j = 0;j < tables.size();j++){
                        if(selectFlag){
                            tableSql.append(" (select * from  `"+tables.get(j)+"`");

                            if(null != logType){
                                params.append(whereSql()).append( "log_type = "+logType);
                            }

                            if(null != eventType){
                                params.append(whereSql()).append( " event_type = "+eventType);
                            }
                            restWhere();
                            selectFlag = false;
                        }else{
                            tableSql.append(" union all select * from `"+tables.get(j)+"`");
                        }

                        tableSql.append(params);
                    }


                }
            }

            tableSql.append(" ) l");
            // (select * from  `log_2_2019_01_01` where log_type = 1004001 and  event_type = 1005007 
            //union all select * from `log_2_2019_01_02` where log_type = 1004001 and  event_type = 1005007 
            //union all select * from `log_2_2019_01_03` where log_type = 1004001 and  event_type = 1005007 ) l
            
            if(!StrUtil.isBlank(startDate) && !StrUtil.isBlank(endDate)){
                tableSql.append(whereSql()).append(" l.logtime > ").append("'"+startDate+"'").append(" and l.logtime < ").append("'"+endDate+"'");

            }

            if(!StrUtil.isBlank(userId)){
                tableSql.append(whereSql()).append(" l.userid = '"+userId+"'");
            }

            if(!StrUtil.isBlank(account)){
                tableSql.append(whereSql()).append(" l.account = '"+account+"'");
            }

            if(!StrUtil.isBlank(roleId)){
                tableSql.append(whereSql()).append(" l.roleid = '"+roleId+"'");
            }

            if(!StrUtil.isBlank(roleName)){
                tableSql.append(whereSql()).append(" l.rolename like '%"+roleName+"'");
            }
            restWhere();
            sql.append(tableSql);

            int totalRow = maxComputeUtil.executeForInt(sql.toString() .replace("l.*","count(*)") +";");

            StringBuffer finalSql = new StringBuffer(" select f.* from (").append(sql).append(") as f");
            List<Record> lists = new ArrayList<>();
            System.err.println(sql);
            System.err.println(tableSql);
            System.err.println(finalSql);

            // select f.* from (select l.* from  (select * from  `log_2_2019_01_01` where log_type = 1004001 and  event_type = 1005007 
            //union all select * from `log_2_2019_01_02` where log_type = 1004001 and  event_type = 1005007 
            //union all select * from `log_2_2019_01_03` where log_type = 1004001 and  event_type = 1005007 ) l 
            //where  l.logtime > '2019-01-01 00:00:00' and l.logtime < '2019-01-03 00:00:00') as f
            
            if(totalRow >1000){
                String tableName = "temp"+String.valueOf(System.currentTimeMillis());
                StringBuffer createTable = new StringBuffer("Create Table ").append(tableName).append(" lifecycle 1 as ").append(finalSql).append(";");
                if(maxComputeUtil.execute(createTable.toString())){
                    lists = maxComputeUtil.tunnel(tableName);
                }
            }else{
            	  System.err.println(finalSql.toString().replace("l.*","l.*,ROW_NUMBER() OVER (ORDER BY l.logtime) AS rank")+";");
                lists = maxComputeUtil.run(finalSql.toString().replace("l.*","l.*,ROW_NUMBER() OVER (ORDER BY l.logtime) AS rank")+";");
            }

          
            List<Log> pageList = new ArrayList<>();

            lists.forEach( record -> {
                Log log = new Log();
                log.setAccount(record.getString("account"));
                log.setEventType(record.getString("event_type"));
                log.setLogname(record.getString("logname"));
                log.setServerid(record.getString("serverid"));
                log.setLogtime(record.getDatetime("logtime"));
                log.setLogType(record.getString("log_type"));
                log.setParams1(record.getString("params1"));
                log.setParams2(record.getString("params2"));
                log.setParams3(record.getString("params3"));
                log.setParams4(record.getString("params4"));
                log.setParams5(record.getString("params5"));
                log.setParams6(record.getString("params6"));
                log.setParams7(record.getString("params7"));
                log.setParams8(record.getString("params8"));
                log.setParams9(record.getString("params9"));
                log.setParams10(record.getString("params10"));
                log.setParams11(record.getString("params11"));
                log.setParams12(record.getString("params12"));
                log.setParams13(record.getString("params13"));
                log.setParams14(record.getString("params14"));
                log.setParams15(record.getString("params15"));
                log.setParams16(record.getString("params16"));
                log.setParams17(record.getString("params17"));
                log.setParams18(record.getString("params18"));
                log.setParams19(record.getString("params19"));
                log.setProgramType(record.getString("program_type"));
                log.setRoleid(record.getString("roleid"));
                log.setRolename(record.getString("rolename"));
                log.setUserid(record.getString("userid"));
                pageList.add(log);
            });
            pageData = new Page<>(pageList,0,0,0,totalRow);
        }catch (Exception e){
            pageData = new Page<>(new ArrayList<>(),1, Const.PAGE_SIZE,0,0);
        }
        return pageData;
    }

    private String whereSql(){

        if(this.whereFlag){
            this.event = " and ";
        }else{
            this.whereFlag = true;
        }
        return this.event;
    }

    private void restWhere(){
        this.whereFlag = false;
        this.event = " where ";
    }



	@Override
	public String findAll(int page, Integer currencytype, Integer currencymin, Integer currencymax, int pageSize, String service,
			String startDate, String endDate, String roleId, String roleName, String userId, String account,
			Integer logType, Integer eventType, String sort) {
		Page<Log> pageData = null;
		currencytype = currencytype == null?0:currencytype;
		currencymin = currencymin == null?0:currencymin;
		currencymax = currencymax == null?0:currencymax;
        String cacheKey = service+startDate+endDate+roleId+roleName+userId+account+logType+eventType;
        Map<String,Object> cacheData = (Map<String,Object>) ehCacheUtils.getCache(cacheKey);
        int startIndex = (page - 1) * pageSize;
        if(null != cacheData){
            List<Log> logList = (List<Log>)cacheData.get("list");
//            int totalRow = (int)cacheData.get("totalRow");
//            int totalPage = totalRow%pageSize == 0 ? totalRow/pageSize : totalRow/pageSize+1;
//            int endIndex = page * pageSize > totalRow?totalRow:page * pageSize;
            logList =   ExchangeLogFormmat.intance.change(logList, currencytype, currencymin, currencymax);
            Page<Log>  tempDate = new Page<>(logList,0,0,0,logList.size()); 
            int totalPage = tempDate.getTotalRow()%pageSize == 0 ? tempDate.getTotalRow()/pageSize : tempDate.getTotalRow()/pageSize+1;
            int endIndex = page * pageSize > tempDate.getTotalRow()?tempDate.getTotalRow():page * pageSize;
            pageData = new Page<>(logList.subList(startIndex,endIndex),page,pageSize,totalPage,tempDate.getTotalRow());
        }else{
            Page<Log> tempDate = this.getData(service, startDate, endDate, roleId, roleName, userId, account,logType, eventType);
            List<Log> logList = tempDate.getList();
            cacheData = new HashMap<>();
            cacheData.put("totalRow",tempDate.getTotalRow());
            cacheData.put("list",logList);
            ehCacheUtils.setCache(cacheKey,cacheData);
            logList =   ExchangeLogFormmat.intance.change(logList, currencytype, currencymin, currencymax);
            tempDate = new Page<>(logList,0,0,0,logList.size()); 
            int totalPage = tempDate.getTotalRow()%pageSize == 0 ? tempDate.getTotalRow()/pageSize : tempDate.getTotalRow()/pageSize+1;
            int endIndex = page * pageSize > tempDate.getTotalRow()?tempDate.getTotalRow():page * pageSize;
            
            pageData = new Page<>(logList.size() > 0?logList.subList(startIndex,endIndex):logList, page, pageSize, totalPage, tempDate.getTotalRow());
        }
      
        return Ret.msgSuccess(pageData);
	}



	@Override
	public String findAll(int page, String goodid, int pageSize, String service, String startDate, String endDate,
			String roleId, String roleName, String userId, String account, Integer logType, Integer eventType,
			String sort) {
		Page<Log> pageData = null;
        String cacheKey = service+startDate+endDate+roleId+roleName+userId+account+logType+eventType;
        Map<String,Object> cacheData = (Map<String,Object>) ehCacheUtils.getCache(cacheKey);
        int startIndex = (page - 1) * pageSize;
        if(null != cacheData){
            List<Log> logList = (List<Log>)cacheData.get("list");
//            int totalRow = (int)cacheData.get("totalRow");
//            int totalPage = totalRow%pageSize == 0 ? totalRow/pageSize : totalRow/pageSize+1;
//            int endIndex = page * pageSize > totalRow?totalRow:page * pageSize;
            logList =   ExchangeLogFormmat.intance.change(logList, goodid);
            Collections.sort(logList, new LogComparator());
            Page<Log>  tempDate = new Page<>(logList,0,0,0,logList.size()); 
            int totalPage = tempDate.getTotalRow()%pageSize == 0 ? tempDate.getTotalRow()/pageSize : tempDate.getTotalRow()/pageSize+1;
            int endIndex = page * pageSize > tempDate.getTotalRow()?tempDate.getTotalRow():page * pageSize;
            pageData = new Page<>(logList.subList(startIndex,endIndex),page,pageSize,totalPage,tempDate.getTotalRow());
        }else{
            Page<Log> tempDate = this.getData(service, startDate, endDate, roleId, roleName, userId, account,logType, eventType);
            List<Log> logList = tempDate.getList();
            cacheData = new HashMap<>();
            cacheData.put("totalRow",tempDate.getTotalRow());
            cacheData.put("list",logList);
            ehCacheUtils.setCache(cacheKey,cacheData);
            logList =   ExchangeLogFormmat.intance.change(logList, goodid);
            Collections.sort(logList, new LogComparator());
            tempDate = new Page<>(logList,0,0,0,logList.size()); 
            int totalPage = tempDate.getTotalRow()%pageSize == 0 ? tempDate.getTotalRow()/pageSize : tempDate.getTotalRow()/pageSize+1;
            int endIndex = page * pageSize > tempDate.getTotalRow()?tempDate.getTotalRow():page * pageSize;
            
            pageData = new Page<>(logList.size() > 0?logList.subList(startIndex,endIndex):logList, page, pageSize, totalPage, tempDate.getTotalRow());
        }
      
        return Ret.msgSuccess(pageData);
	}



	@Override
	public String findAll(int page, Integer id,Integer status, int pageSize, String service, String startDate, String endDate,
			String roleId, String roleName, String userId, String account, Integer logType, Integer eventType,
			String sort) {
		Page<Log> pageData = null;
        String cacheKey = service+startDate+endDate+roleId+roleName+userId+account+logType+eventType;
        Map<String,Object> cacheData = (Map<String,Object>) ehCacheUtils.getCache(cacheKey);
        int startIndex = (page - 1) * pageSize;
        if(null != cacheData){
            List<Log> logList = (List<Log>)cacheData.get("list");
//            int totalRow = (int)cacheData.get("totalRow");
//            int totalPage = totalRow%pageSize == 0 ? totalRow/pageSize : totalRow/pageSize+1;
//            int endIndex = page * pageSize > totalRow?totalRow:page * pageSize;
            logList =   ExchangeLogFormmat.intance.change(logList, id,status);
            Collections.sort(logList, new LogComparator());
            Page<Log>  tempDate = new Page<>(logList,0,0,0,logList.size()); 
            int totalPage = tempDate.getTotalRow()%pageSize == 0 ? tempDate.getTotalRow()/pageSize : tempDate.getTotalRow()/pageSize+1;
            int endIndex = page * pageSize > tempDate.getTotalRow()?tempDate.getTotalRow():page * pageSize;
            pageData = new Page<>(logList.subList(startIndex,endIndex),page,pageSize,totalPage,tempDate.getTotalRow());
        }else{
            Page<Log> tempDate = this.getData(service, startDate, endDate, roleId, roleName, userId, account,logType, eventType);
            List<Log> logList = tempDate.getList();
            cacheData = new HashMap<>();
            cacheData.put("totalRow",tempDate.getTotalRow());
            cacheData.put("list",logList);
            ehCacheUtils.setCache(cacheKey,cacheData);
            logList =   ExchangeLogFormmat.intance.change(logList, id,status);
            Collections.sort(logList, new LogComparator());
            tempDate = new Page<>(logList,0,0,0,logList.size()); 
            int totalPage = tempDate.getTotalRow()%pageSize == 0 ? tempDate.getTotalRow()/pageSize : tempDate.getTotalRow()/pageSize+1;
            int endIndex = page * pageSize > tempDate.getTotalRow()?tempDate.getTotalRow():page * pageSize;
            
            pageData = new Page<>(logList.size() > 0?logList.subList(startIndex,endIndex):logList, page, pageSize, totalPage, tempDate.getTotalRow());
        }
      
        return Ret.msgSuccess(pageData);
	}
}
