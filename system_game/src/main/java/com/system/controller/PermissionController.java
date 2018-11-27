package com.system.controller;

import com.system.bean.Permission;
import com.system.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzy
 * @Date 2018/7/11 下午12:38
 */
@Api(description = "权限操作接口")
@RestController
@RequestMapping("/per")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加权限接口",notes = "根据Permission对象创建权限")
    public String addPermission(@ModelAttribute Permission permission){
        return permissionService.addPermission(permission);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新权限接口",notes = "根据Permission对象更新权限,id必传")
    @ApiImplicitParam(name = "id",value = "主键",required = true,paramType = "query",dataType = "Integer")
    public String updatePermission(@ModelAttribute Permission permission){
        return permissionService.updatePermission(permission);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取权限详情",notes = "根据id获取权限详细内容")
    @ApiImplicitParam(name = "id",value = "主键",required = true,paramType = "path",dataType = "Integer")
    public String getPermissionById(@PathVariable Integer id){
        return permissionService.getPermissionById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除权限",notes = "根据id删除权限")
    @ApiImplicitParam(name = "id",value = "主键",required = true,paramType = "path",dataType = "Integer")
    public String deletePermission(@PathVariable Integer id){
        return permissionService.deletePermission(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取权限列表",notes = "根据id获取权限列表")
    public String getTreeList(){
        return permissionService.getTreeList();
    }

    @RequestMapping(value = "/checkPath", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取权限详情",notes = "根据id获取权限详细内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path",value = "路径",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "parentId",value = "父级id",required = false,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "id",value = "主键",required = false,paramType = "query",dataType = "Integer")
    })
    public String checkPath(String path,Integer parentId,Integer id){

        return permissionService.checkPath(path,parentId,id);
    }

    @RequestMapping(value = "/changeSort", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "移动排序",notes = "移动排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "flag",value = "上移下移标识(up,down)",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "parentId",value = "父级id",required = false,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "sort",value = "排序",required = true,paramType = "query",dataType = "Integer")
    })
    public String changeSort(String flag,Integer parentId,Integer sort){
        return permissionService.changeSort(flag,parentId,sort);
    }
}
