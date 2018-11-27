package com.system.service;

import com.system.bean.Admin;
import com.system.config.shiro.FeignLogConfiguration;
import com.system.service.hystrix.AdminServiceHystrix;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.beans.Encoder;

/**
 * @author zzy
 * @Date 2018/8/16 上午11:58
 */
@FeignClient(name = "systemAdmin",fallback = AdminServiceHystrix.class,configuration = FeignLogConfiguration.class)
public interface AdminService {

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public Admin login(@RequestParam("account") String account,@RequestParam("password") String password);

    @PostMapping(value = "/admin", produces = "application/json;charset=UTF-8")
    String addAdmin(@RequestBody Admin admin);

    @GetMapping(value = "/admin/{id}", produces = "application/json;charset=UTF-8")
    String getAdminById(@PathVariable("id") Integer id);

    @PutMapping(value = "/admin", produces = "application/json;charset=UTF-8")
    String update(Admin admin);

    @GetMapping(value = "/admin/page/{page}", produces = "application/json;charset=UTF-8")
    String getAdminPage(@PathVariable("page") Integer page,@RequestParam("pageSize") Integer pageSize);

    @PostMapping(value = "/admin/checkAccount")
    String checkAccount(@RequestParam("account") String account,@RequestParam("id") Integer id);

    @DeleteMapping(value = "/admin/{id}")
    String delAdmin (@PathVariable("id") Integer [] id);
}
