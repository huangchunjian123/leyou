package com.system.controller;

import com.system.bean.Admin;
import com.system.common.util.Ret;
import com.system.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author zzy
 */
@Api(description = "管理员操作接口")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "创建管理员",notes = "根据admin对象创建管理员")
    @ApiImplicitParam(name = "headPortrait",value = "头像",required = false,paramType = "query",dataType = "String")
    public String addAdmin(@ModelAttribute Admin admin, HttpServletRequest request){
        return adminService.addAdmin(admin);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取管理员详情",notes = "根据id获取管理员详细内容")
    @ApiImplicitParam(name = "id",value = "管理员id",required = true,paramType = "path",dataType = "Integer")
    public String getAdminById(@PathVariable Integer id){

        if(null == id){
            return Ret.msgSetVal("管理员id不能为空");
        }

        return adminService.getAdminById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新管理员", notes = "更新管理员信息")
    public String update(@ModelAttribute Admin admin){
        return adminService.update(admin);
    }

    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取管理员列表",notes = "根据页数获取管理员列表")
    @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "分页行数", required = false)
    public String getAdminPage(@PathVariable Integer page,Integer pageSize){

        return adminService.getAdminPage(page,pageSize);
    }

    @RequestMapping(value = "/checkAccount", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "验证管理员帐号是否可用",notes = "根据account(必传)/id(可选)验证管理员帐号是否可用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "account", value = "帐号", required = true),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "id", value = "管理员id")
    })
    public String checkAccount(String account,Integer id){

        return adminService.checkAccount(account,id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除管理员", notes = "根据id删除管理员")
    @ApiImplicitParam(name = "id",value = "管理员id",required = true,paramType = "path",dataType = "Integer")
    public String delAdmin(@PathVariable Integer [] id){
        return adminService.delAdmin(id);
    }


}
