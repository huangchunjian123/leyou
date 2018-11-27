package com.provide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SystemProvideApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemProvideApplication.class, args);
	}
}
