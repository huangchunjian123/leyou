package com.system.service;

import com.system.bean.Permission;
import com.system.service.hystrix.PermissionServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zzy
 * @Date 2018/8/17 下午1:38
 */
@FeignClient(name = "systemAdmin", fallback = PermissionServiceHystrix.class)
public interface PermissionService {

    @RequestMapping(value = "/per", method = RequestMethod.POST)
    public String addPermission(@RequestBody Permission permission);

    @RequestMapping(value = "/per", method = RequestMethod.PUT)
    public String updatePermission(@RequestBody Permission permission);

    @RequestMapping(value = "/per/{id}", method = RequestMethod.GET)
    public String getPermissionById(@PathVariable("id") Integer id);

    @RequestMapping(value = "/per/{id}", method = RequestMethod.DELETE)
    public String deletePermission(@PathVariable("id") Integer id);

    @RequestMapping(value = "/per", method = RequestMethod.GET)
    public String getTreeList();

    @RequestMapping(value = "/per/list", method = RequestMethod.POST)
    List<Permission> getRoleFunction(@RequestParam("roleId") int roleId);

    @RequestMapping(value = "/per/checkPath", method = RequestMethod.GET)
    public String checkPath(@RequestParam("path") String path,@RequestParam("parentId") Integer parentId,@RequestParam("id") Integer id);

    @RequestMapping(value = "/per/changeSort", method = RequestMethod.PUT)
    public String changeSort(@RequestParam("flag") String flag,@RequestParam("parentId") Integer parentId,@RequestParam("sort") Integer sort);
}
