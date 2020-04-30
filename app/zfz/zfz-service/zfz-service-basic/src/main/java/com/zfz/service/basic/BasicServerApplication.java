package com.zfz.service.basic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * author: DreamSaddle
 * date: 2019年12月26日
 * time: 17:25
 */
@MapperScan("com.zfz.service.basic.dao")
@ComponentScan({
		"com.zfz.common",
		"com.zfz.service.basic.*",
		"com.zfz.db.config"})
@EnableDiscoveryClient
@SpringBootApplication
public class BasicServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicServerApplication.class, args);
	}
}
