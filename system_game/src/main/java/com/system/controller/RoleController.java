package com.system.controller;

import com.system.bean.Role;
import com.system.common.Const;
import com.system.common.util.Ret;
import com.system.common.util.StrKit;
import com.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzy
 * @Date 2018/7/16 上午11:34
 */
@Api(description = "角色操作接口")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "创建角色",notes = "根据role对象创建角色")
    public String addRole(@ModelAttribute Role role){
        return roleService.addRole(role);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新角色",notes = "根据role对象更新角色")
    public String updateRole(@ModelAttribute Role role){
        return roleService.updateRole(role);
    }

    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取角色列表",notes = "根据页数获取角色列表")
    @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "分页行数", required = false)
    public String getPage(@PathVariable Integer page,Integer pageSize){
        return roleService.getPage(page,pageSize);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取全部角色列表",notes = "获取全部角色列表")
    public String getList(){
        return roleService.getList();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取角色列表",notes = "根据页数获取角色列表")
    public String getRole(@PathVariable Integer id){
        return roleService.getRole(id);
    }

    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除角色", notes = "根据id删除角色员")
    @ApiImplicitParam(name = "ids",value = "角色id",required = true,paramType = "path",dataType = "Integer")
    public String deleteRole(@PathVariable Integer [] ids){
        return roleService.deleteRole(ids);
    }

    @RequestMapping(value = "/per/{role}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "角色分配权限", notes = "角色分配权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role",value = "角色id",required = true, paramType = "path",dataType = "int"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "permissions", value = "权限id数组", required = true)
    })
    public String addRolePer(@PathVariable Integer role,String permissions){
        return roleService.addRolePer(role,permissions);
    }

    @RequestMapping(value = "/per/{role}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "角色权限列表", notes = "角色权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role",value = "角色id",required = true, paramType = "path",dataType = "int")
    })
    public String getRolePer(@PathVariable Integer role){
        return roleService.getRolePer(role);
    }

    @RequestMapping(value = "/per/list/{role}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取角色权限列表",notes = "根据角色id获取权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role",value = "角色id",required = true, paramType = "path",dataType = "int")
    })
    public String getTreeList(@PathVariable Integer role){
        return roleService.getTreeList(role);
    }
}
