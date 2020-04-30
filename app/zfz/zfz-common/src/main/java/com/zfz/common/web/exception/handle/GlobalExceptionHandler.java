package com.zfz.common.web.exception.handle;

import com.zfz.common.api.Result;
import com.zfz.common.web.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BizException.class)
	public Result bizExceptionHandler(HttpServletRequest request, BizException ex) {
		log.info("业务异常: {}", ex.getMessage());
		return Result.failed(ex.getMessage());
	}


	@ExceptionHandler(Exception.class)
	public Result exceptionHandler(HttpServletRequest request, Exception ex) {
		log.error("系统未知异常: {}", ex.getMessage());
		return Result.failed("系统异常");
	}
}
