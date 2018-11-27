package com.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @EnableSwagger2 spring Boot构建一个较为完成的RESTful APIs
 * @EnableFeignClients通过当前service服务要调用到其他service服务的api接口时，可通过EnableFeignClients调用其他服务的api，
 * @EnableHystrixDashboard可视化性能监控
 * @author huangchunjian
 *
 * 乐游网络科技有限公司
   huangchunjian1741@dingtalk.com
 */
@SpringBootApplication
@EnableFeignClients
@EnableHystrixDashboard
@EnableSwagger2
public class SystemGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemGameApplication.class, args);
	}
}

