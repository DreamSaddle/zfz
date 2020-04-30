package com.zfz.common.annotation;

import java.lang.annotation.*;

/**
 * controller BindingResult 注解参数校验结果统一处理注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BindingResultValid {
}
