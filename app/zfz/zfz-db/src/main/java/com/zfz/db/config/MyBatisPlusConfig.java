package com.zfz.db.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.zfz.db.handler.ZfzMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author: DreamSaddle
 * date: 2020年01月04日
 * time: 14:59
 */
@Configuration
public class MyBatisPlusConfig {

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	@Bean
	public MetaObjectHandler metaObjectHandler() {
		return new ZfzMetaObjectHandler();
	}
}
