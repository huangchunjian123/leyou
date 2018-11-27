package com.system.controller;

import com.system.bean.Admin;
import com.system.common.util.DateUtil;
import com.system.common.util.Ret;
import com.system.service.AdminService;
import com.system.service.hystrix.AdminServiceHystrix;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/")
@Api(description = "公开默认操作接口")
@Slf4j
public class DefaultController {

    @Value("${web.upload-path}")
    private String filePath;
    private String staticPath = "static/";
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminServiceHystrix adminServiceHystrix;


    @RequestMapping(value="upload/file",method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
    @ApiOperation(value = "文件上传",notes = "上传文件接口")
    public String uploadFile(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws Exception {
        //String contentType = file.getContentType();
        String fileName = this.reNameFile(file.getOriginalFilename());
        String realFilePath = filePath.substring(filePath.indexOf(":")+1,filePath.length());
        try {
            //获取静态上传路径
            //filePath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath()+STATIC_PATH.replace("%20"," ");
            uploadFile(file.getBytes(), realFilePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("图片上传失败");
            throw e;
        }
        return Ret.msgSuccess(staticPath+fileName);

    }

    @RequestMapping(value="login",method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
    @ApiOperation(value = "登入接口",notes = "根据用户名，密码登入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "帐号",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "password",value = "密码",required = true,paramType = "query",dataType = "String")
    })
    public String login(String username,String password){
        //adminServiceHystrix.login(username,password);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);

        try {
            subject.login(usernamePasswordToken);
            Map<Object,Object> token = new HashMap<>();
            Admin admin = ((Admin)subject.getPrincipal());
            token.put("token",admin.getId());
            Admin newAdmin = new Admin();
            //Admin newAdmin = admin.deepClone();
            newAdmin.setId(admin.getId());
            newAdmin.setLastLogin(new Date());
            newAdmin.setLastLoginIp(((WebDelegatingSubject) subject).getHost());
            adminService.update(newAdmin);
            return Ret.msgSuccess(0,"登入成功",token);
        } catch (AuthenticationException e){
            return Ret.msgSetVal(e.getMessage());
        } catch (Exception e){
            return Ret.msgSetVal("登入失败");
        }
    }

    @RequestMapping(value="admin/logout",method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
    @ApiOperation(value = "退出登入",notes = "退出登入")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Ret.msgSuccess(0,"退出成功！",null);
    }

    @RequestMapping(value="admin/info", method = RequestMethod.GET, produces={"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取管理员信息",notes = "获取管理员信息")
    //@RequiresPermissions("upload")
    public String getAdminInfo(){
        Subject subject = SecurityUtils.getSubject();
        Object object = subject.getPrincipal();

        if(null == object){
            return Ret.msgLoginErr();
        }
        return Ret.msgSuccess((Admin)object);
    }

    @RequestMapping(value="auth/err",method = RequestMethod.GET, produces={"application/json;charset=UTF-8"})
    public String unAuth(){
        return Ret.msgLoginErr();
    }


    public void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);

        if(false == targetFile.exists()){
            targetFile.mkdirs();
        }

        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    private String reNameFile(String fileName){
        String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
        return DateUtil.formatDate(new Date(),"yyyyMMddhhssmmSSS")+ UUID.randomUUID()+suffix;
    }

}
