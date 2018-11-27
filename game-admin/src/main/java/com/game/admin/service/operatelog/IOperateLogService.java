package com.game.admin.service.operatelog;

import java.util.Date;

import com.game.admin.pojo.system.OperateLogData;
import com.game.api.hessian.DataGrid;

/**
 * 操作日志接口
 * @author huangchunjian
 *
 */
public interface IOperateLogService {
	
	/**
	 * 根据日志Id获取日志
	 * @param id
	 * @return
	 */
	public OperateLogData get(int id);
	
	/**
	 * 查询日志datagrid
	 * @param page
	 * @param rows
	 * @return
	 */
	public DataGrid datagrid(int page,int rows);
	
	/**
	 * 添加日志
	 * @param operator
	 * @param event
	 * @param status
	 * @param logTime
	 */
	public void add(String operator,String event,String status,Date logTime);
	
	/**
	 * 删除日志
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * 条件查询
	 * @param operatorsName
	 * @param startTime
	 * @param endTime
	 * @param keyword
	 * @return
	 */
	public DataGrid  paramsQuery(int page,int rows,String operatorsName,String startTime,String endTime,String keyword );
	
}
