package com.zfz.service.basic;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.zfz.common.aspect.BindingResultValidAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * author: DreamSaddle
 * date: 2019年12月26日
 * time: 17:25
 */
@MapperScan("com.zfz.service.basic.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class BasicServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicServerApplication.class, args);
	}


	@Bean
	public BindingResultValidAspect bindingResultValidAspect() {
		return new BindingResultValidAspect();
	}

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
}
