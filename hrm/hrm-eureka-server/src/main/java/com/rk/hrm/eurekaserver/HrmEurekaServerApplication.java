package com.rk.hrm.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HrmEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrmEurekaServerApplication.class, args);
	}

}
