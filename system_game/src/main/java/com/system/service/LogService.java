package com.system.service;

import com.system.config.shiro.FeignLogConfiguration;
import com.system.service.hystrix.LogServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzy
 * @Date 2018/8/16 上午11:58
 */
@FeignClient(name = "systemLog",fallback = LogServiceHystrix.class,configuration = FeignLogConfiguration.class)
public interface LogService {
    @RequestMapping(value = "/log/{page}", method = RequestMethod.GET)
    String findAll(@PathVariable("page") Integer page, @RequestParam("pageSize") Integer pageSize,@RequestParam("service") String service,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate,@RequestParam("roleId") String roleId,@RequestParam("roleName") String roleName,@RequestParam("userId") String userId,@RequestParam("account") String account,@RequestParam("logType") Integer logType,@RequestParam("eventType") Integer eventType,@RequestParam("sort") String sort);


    @RequestMapping(value = "/log/person", method = RequestMethod.GET)
    String findPerson(@RequestParam("service") String service,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate,@RequestParam("roleId") String roleId,@RequestParam("roleName") String roleName,@RequestParam("userId") String userId,@RequestParam("account") String account,@RequestParam("logType") Integer logType,@RequestParam("eventType") Integer eventType);
}
