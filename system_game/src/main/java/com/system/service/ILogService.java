package com.system.service;


public interface ILogService {

    String findAll(int page, int pageSize, String service, String startDate, String endDate, String roleId, String roleName, String userId, String account, Integer logType, Integer eventType, String sort);

}
