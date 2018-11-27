package com.system.controller;

import com.system.bean.Permission;
import com.system.common.util.Ret;
import com.system.common.util.StrUtil;
import com.system.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zzy
 * @Date 2018/7/11 下午12:38
 */
@Api(description = "权限操作接口")
@RestController
@RequestMapping("/per")
public class PermissionController {

    @Autowired
    IPermissionService permissionService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加权限接口",notes = "根据Permission对象创建权限")
    public String addPermission(@RequestBody Permission permission){
        if(permissionService.save(permission)){
            return Ret.msgAdd();
        }

        return Ret.msgAddFail();
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "添加权限接口",notes = "根据Permission对象创建权限")
    @ApiImplicitParam(name = "roleId",value = "角色id",required = true,paramType = "query",dataType = "Integer")
    public List<Permission> getRoleFunction(@RequestParam("roleId") int roleId){
        return permissionService.getRoleFunction(roleId);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新权限接口",notes = "根据Permission对象更新权限,id必传")
    @ApiImplicitParam(name = "id",value = "主键",required = true,paramType = "query",dataType = "Integer")
    public String updatePermission(@RequestBody Permission permission){
        if(null == permission.getId()){
            return Ret.msgSetVal("id为必传参数");
        }

        if(permissionService.update(permission)){
            return Ret.msgUpdate();
        }

        return Ret.msgUpdateFail();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取权限详情",notes = "根据id获取权限详细内容")
    @ApiImplicitParam(name = "id",value = "主键",required = true,paramType = "path",dataType = "Integer")
    public String getPermissionById(@PathVariable Integer id){
        return Ret.msgSuccess(permissionService.getById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除权限",notes = "根据id删除权限")
    @ApiImplicitParam(name = "id",value = "主键",required = true,paramType = "path",dataType = "Integer")
    public String deletePermission(@PathVariable Integer id){
        if(permissionService.deleteById(id)){
            return Ret.msgDel();
        }

        return Ret.msgDelFail();
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取权限列表",notes = "根据id获取权限列表")
    public String getTreeList(){
        return Ret.msgSuccess(permissionService.getTreeList(null));
    }

    @RequestMapping(value = "/checkPath", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取权限详情",notes = "根据id获取权限详细内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path",value = "路径",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "parentId",value = "父级id",required = false,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "id",value = "主键",required = false,paramType = "query",dataType = "Integer")
    })
    public String checkPath(String path,Integer parentId,Integer id){
        Map<Object, Object> result = new HashMap<>();
        if(StrUtil.isBlank(path)){
            return Ret.msgSetVal("path参数不能为空");
        }

        if(permissionService.checkPath(path.trim(),parentId,id)){
            result.put("result",true);
            return Ret.msgSuccess(result);
        }
        result.put("result",false);
        return Ret.msgSuccess("该路径已存在",result);
    }
}
