package com.system.service.hystrix;

import com.system.bean.Admin;
import com.system.service.LogService;
import org.springframework.stereotype.Component;

/**
 * @author zzy
 * @Date 2018/8/25 上午9:28
 */
@Component
public class LogServiceHystrix implements LogService {

    @Override
    public String findAll(Integer page, Integer pageSize, String service, String startDate, String endDate, String roleId, String roleName, String userId, String account, Integer logType, Integer eventType, String sort) {
        return null;
    }

    @Override
    public String findPerson(String service, String startDate, String endDate, String roleId, String roleName, String userId, String account, Integer logType, Integer eventType) {
        return null;
    }
}
