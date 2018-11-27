package com.system.service;

import com.system.bean.Role;
import com.system.config.shiro.FeignLogConfiguration;
import com.system.service.hystrix.RoleServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzy
 * @Date 2018/8/17 下午1:38
 */
@FeignClient(name = "systemAdmin", fallback = RoleServiceHystrix.class,configuration = FeignLogConfiguration.class)
public interface RoleService {
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    String addRole(@RequestBody Role role);

    @RequestMapping(value = "/role", method = RequestMethod.PUT)
    String updateRole(@RequestBody Role role);

    @RequestMapping(value = "/role/page/{page}", method = RequestMethod.GET)
    String getPage(@PathVariable("page") Integer page, @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    String getList();

    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    String getRole(@PathVariable("id") Integer id);

    @RequestMapping(value = "/role/{ids}", method = RequestMethod.DELETE)
    String deleteRole(@PathVariable("ids") Integer [] ids);

    @RequestMapping(value = "/role/per/{role}", method = RequestMethod.POST)
    String addRolePer(@PathVariable("role") Integer role,@RequestParam("permissions") String permissions);

    @RequestMapping(value = "/role/per/{role}", method = RequestMethod.GET)
    String getRolePer(@PathVariable("role") Integer role);

    @RequestMapping(value = "/role/per/list/{role}", method = RequestMethod.GET)
    String getTreeList(@PathVariable("role") Integer role);
}
