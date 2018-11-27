package com.system.aop;

import com.system.common.util.Ret;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzy
 * @Date 2018/8/25 上午9:33
 */
@Aspect   //定义一个切面
@Configuration
public class ServiceHystrixAspect {
    protected static final Logger logger = LoggerFactory.getLogger(ServiceHystrixAspect.class);

    // 定义切点Pointcut
    @Pointcut("execution(* com.system.service.hystrix..*.*(..))")
    public void excudeService() {
    }

    /*@Before("excudeService()")
    public void before(JoinPoint joinPoint) {
        logger.info("request {} bad",joinPoint.getSignature().getName());
    }*/

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        if(String.class == pjp.getSignature().getDeclaringType()){
            return Ret.msgSetVal("服务器内部请求错误");
        }

        return pjp.proceed();
    }
}
