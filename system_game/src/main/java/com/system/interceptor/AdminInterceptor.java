package com.system.interceptor;

import com.system.aop.ServiceHystrixAspect;
import com.system.bean.Admin;
import com.system.common.util.Ret;
import com.system.common.util.StrKit;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zzy
 * @Date 2018/8/26 下午4:27
 */
public class AdminInterceptor implements HandlerInterceptor{
    private static final Logger logger = LoggerFactory.getLogger(AdminInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        String token = request.getHeader("X-Token");

        if (StrKit.isBlank(token) && !"/login".equals(uri) && !"/upload/file".equals(uri) && !"/auth/err".equals(uri)) {
            String responseMsg = Ret.msgSuccess(-6,"请求头token不能为空！",null);
            this.responseWriter(response,responseMsg);
            return false;
        } else if(!"/login".equals(uri) && !"/upload/file".equals(uri) && !"/auth/err".equals(uri)){
            Subject subject = SecurityUtils.getSubject();
            if (false == token.equals(((Admin)subject.getPrincipal()).getId().toString())){
                this.responseWriter(response,Ret.msgLoginErr());
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
            throws Exception {
        System.out.println("after");

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        System.out.println("post");

    }

    private void responseWriter(HttpServletResponse response,String jsonData) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");

        try {
            writer = response.getWriter();
            writer.print(jsonData);

        } catch (IOException e) {
            logger.error("response error",e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

}
