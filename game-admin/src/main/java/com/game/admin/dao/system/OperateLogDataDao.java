package com.game.admin.dao.system;

import java.util.List;

import com.game.admin.pojo.system.OperateLogData;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;
import net.paoding.rose.jade.core.Identity;

/**
 * 操作日志Dao层
 * @author huangchunjian
 *
 */
@DAO
public interface OperateLogDataDao {
	
	
	@SQL("insert into t_operate_log(operator,event,status,log_time)values(:data.operator,:data.event,:data.status,:data.logTime);")
	public Identity save(@SQLParam("data") OperateLogData data);
	
	@SQL("select id,operator,event,status,log_time from t_operate_log where id=:id ")
	public OperateLogData get(@SQLParam("id") int id);
	
	@SQL("select id,operator,event,status,log_time from t_operate_log")
	public List<OperateLogData> getAll();
	
	@SQL("delete from t_operate_log as logTime  where id =:id ")
	public OperateLogData delete(@SQLParam("id") int id);
	
	
}
