package com.system.aop;

import com.system.bean.Admin;
import com.system.common.util.Ret;
import com.system.common.util.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author zzy
 * @Date 2018/7/9 下午2:08
 */
@Aspect   //定义一个切面
@Component
@Slf4j
public class LogRecordAspect {
    // 定义切点Pointcut
    @Pointcut("execution(* com.system.controller..*.*(..))")
    public void excudeService() {
        System.out.println("excudeService");
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = "";

        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            queryString += paraName+": "+request.getParameter(paraName)+"\t";
        }


        String token = request.getHeader("X-Token");
        log.info("请求开始\n======================================================== \n url: {}  \n method: {}  \n uri: {}  \n params: {}  \n token: {} \n========================================================", url, method, uri, queryString, token);
        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        log.info("请求结束，controller的返回值是 " + result);
        return result;
    }
}
