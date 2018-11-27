package com.system.config.shiro;

import com.system.common.util.Ret;
import net.sf.json.JSONObject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zzy
 * @Date 2018/8/27 下午12:50
 * shiro---权限不足
 */
public class ShiroPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if(req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            resp.setStatus(HttpStatus.OK.value());
            return true;
        }
        //前端Ajax请求时requestHeader里面带一些参数，用于判断是否是前端的请求
        String ajaxHeader = req.getHeader("X-Requested-With");
        if (ajaxHeader != null || req.getHeader("X-Requested-With") != null) {
            //前端Ajax请求，则不会重定向
            resp.setHeader("Access-Control-Allow-Origin",  req.getHeader("Origin"));
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.setContentType("application/json; charset=utf-8");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.println(Ret.msgAuthErr());
            out.flush();
            out.close();
            return false;
        }
        return true;
    }
}
