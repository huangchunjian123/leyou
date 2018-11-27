package com.system.exception;

import com.system.common.util.Ret;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zzy
 * @Date 2018/8/27 下午2:33
 */
public class ExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
        ModelAndView mv = new ModelAndView();

        if (ex instanceof UnauthorizedException) {
            //设置状态码
            httpServletResponse.setStatus(HttpStatus.OK.value());
            //设置ContentType
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            //避免乱码
            httpServletResponse.setCharacterEncoding("UTF-8");
            try {
                httpServletResponse.getWriter().write(Ret.msgAuthErr());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mv;

    }
}
