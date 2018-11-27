package com.system;

import com.system.exception.ExceptionHandler;
import com.system.interceptor.AdminInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author zzy
 * @Date 2018/8/26 下午4:38
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {
    @Value("${web.upload-path}")
    private String filePath;

    /**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
     * 需要重新指定静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/").addResourceLocations(filePath);
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Bean
    public AdminInterceptor getAdminInterceptor(){
        return new AdminInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAdminInterceptor())
                .addPathPatterns("/admin**")
                .addPathPatterns("/dic**")
                .addPathPatterns("/per**")
                .addPathPatterns("/role**");
        super.addInterceptors(registry);
    }

    /**
     * 注册全局异常处理
     * @return
     */
    @Override
    public HandlerExceptionResolver handlerExceptionResolver() {
        return new ExceptionHandler();
    }

}
