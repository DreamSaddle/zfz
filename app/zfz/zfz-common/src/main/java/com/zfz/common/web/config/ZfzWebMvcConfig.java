package com.zfz.common.web.config;

import com.zfz.common.web.SingleValueParameterResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ZfzWebMvcConfig implements WebMvcConfigurer {

	@Bean
	public SingleValueParameterResolver singleValueParameterResolver() {
		return new SingleValueParameterResolver();
	}


	@Override
	public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(singleValueParameterResolver());
	}
}
