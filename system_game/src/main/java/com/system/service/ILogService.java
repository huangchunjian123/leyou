package com.system.service;


public interface ILogService {
	String findAll(int page,Integer id,Integer status, int pageSize, String service, String startDate, String endDate, String roleId, String roleName, String userId, String account, Integer logType, Integer eventType, String sort);
    String findAll(int page, int pageSize, String service, String startDate, String endDate, String roleId, String roleName, String userId, String account, Integer logType, Integer eventType, String sort);
    String findAll(int page,Integer yuanbaotype,Integer numbermin,Integer numbermax, int pageSize, String service, String startDate, String endDate, String roleId, String roleName, String userId, String account, Integer logType, Integer eventType, String sort);
    String findAll(int page,String goodid, int pageSize, String service, String startDate, String endDate, String roleId, String roleName, String userId, String account, Integer logType, Integer eventType, String sort);
}
