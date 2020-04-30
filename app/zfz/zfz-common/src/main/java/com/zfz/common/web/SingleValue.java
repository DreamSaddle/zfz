package com.zfz.common.web;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SingleValue {

	/**
	 * 请求 json key 值, 如果没有则默认是对应参数名
	 * @return
	 */
	String value() default "";
}
