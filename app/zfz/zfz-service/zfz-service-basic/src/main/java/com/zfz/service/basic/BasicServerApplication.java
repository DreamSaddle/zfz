package com.zfz.service.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * author: DreamSaddle
 * date: 2019年12月26日
 * time: 17:25
 */
@EnableDiscoveryClient
@SpringBootApplication
public class BasicServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicServerApplication.class, args);
	}

}
