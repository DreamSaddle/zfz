package com.zfz.common.aspect;

import com.zfz.common.api.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * author: DreamSaddle
 * date: 2020年01月03日
 * time: 16:42
 */
@Aspect
@Component
public class BindingResultValidAspect {

	@Pointcut("@annotation(com.zfz.common.annotation.BindingResultValid)")
	public void pointCut() {}

	@Around("pointCut()")
	Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean hasError = false;
		String message = "";
		Object[] args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			for (final Object arg : args) {
				if (arg instanceof BindingResult) {
					BindingResult bindingResult = (BindingResult) arg;
					List<ObjectError> errors = bindingResult.getAllErrors();
					if (errors.size() > 0) {
						hasError = true;
						message = errors.get(0).getDefaultMessage();
					}
					break;
				}
			}
		}

		if (hasError) {
			return Result.validateFailed(message);
		}

		return joinPoint.proceed();
	}

}
