package com.system.controller;

import com.system.bean.Admin;
import com.system.common.Const;
import com.system.common.util.Ret;
import com.system.common.util.StrUtil;
import com.system.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author zzy
 */
@Api(description = "管理员操作接口")
@RestController
@RequestMapping("/admin")
public class AdminController {
    protected static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final String DEFAULT_PWD = StrUtil.getMD5("123456");

    @Autowired
    IAdminService adminService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "管理员登入",notes = "根据管理员登入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account",value = "用户名",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "password",value = "密码",required = true,paramType = "query",dataType = "String")
    })
    public Admin login(@RequestParam("account") String account,@RequestParam("password") String password,HttpServletRequest request){
        logger.info("===<call login, TraceId={}, SpanId={}>===",
                request.getHeader("X-B3-TraceId"), request.getHeader("X-B3-SpanId"));

        return adminService.login(account, password);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "创建管理员",notes = "根据admin对象创建管理员")
    @ApiImplicitParam(name = "headPortrait",value = "头像",required = false,paramType = "query",dataType = "String")
    public String addAdmin(@RequestBody Admin admin, HttpServletRequest request){

        if(StrUtil.isBlank(admin.getAccount())){
            return Ret.msgSetVal("account为必传参数");
        }

        if(!adminService.checkAcc(admin.getAccount(),null)){
            return Ret.msgSetVal("帐号:"+admin.getAccount()+" 已存在");
        }

        admin.setPassword(DEFAULT_PWD);
        admin.setCreateDate(new Date());
        if(adminService.save(admin)){
           return Ret.msgAdd();
        }

        return Ret.msgAddFail();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取管理员详情",notes = "根据id获取管理员详细内容")
    @ApiImplicitParam(name = "id",value = "管理员id",required = true,paramType = "path",dataType = "Integer")
    public String getAdminById(@PathVariable Integer id){

        if(null == id){
            return Ret.msgSetVal("管理员id不能为空");
        }

        return Ret.msgSuccess(adminService.getById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新管理员", notes = "更新管理员信息")
    public String update(@RequestBody Admin admin){
        if(StrUtil.isBlank(admin.getAccount())){
            return Ret.msgSetVal("account为必传参数");
        }else if(null == admin.getId()){
            return Ret.msgSetVal("id为必传参数");
        }

        if(!adminService.checkAcc(admin.getAccount(),admin.getId())){
            return Ret.msgSetVal("帐号:"+admin.getAccount()+" 已存在");
        }

        if(adminService.update(admin)){
            return Ret.msgUpdate();
        }
        return Ret.msgUpdateFail();

    }

    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取管理员列表",notes = "根据页数获取管理员列表")
    @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "分页行数", required = false)
    public String getAdminPage(@PathVariable Integer page,Integer pageSize){

        if(null == pageSize){
            pageSize = Const.PAGE_SIZE;
        }

       return Ret.msgSuccess(adminService.getPage(page, pageSize));
    }

    @RequestMapping(value = "/checkAccount", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "验证管理员帐号是否可用",notes = "根据account(必传)/id(可选)验证管理员帐号是否可用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "account", value = "帐号", required = true),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "id", value = "管理员id")
    })
    public String checkAccount(String account,Integer id){
        Map<Object, Object> result = new HashMap<>();
        if(null == account){
            return Ret.msgSetVal("account为必传参数");
        }

        if(adminService.checkAcc(account,id)){
            result.put("result",true);
            return Ret.msgSuccess(result);
        }
        result.put("result",false);
        return Ret.msgSuccess("该帐号已存在",result);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除管理员", notes = "根据id删除管理员")
    @ApiImplicitParam(name = "id",value = "管理员id",required = true,paramType = "path",dataType = "Integer")
    public String delAdmin(@PathVariable Integer [] id){
        if(adminService.delete(id)){
            return Ret.setSuccessMsg("删除成功");
        }
        return Ret.msgSetVal("删除失败");
    }


}
