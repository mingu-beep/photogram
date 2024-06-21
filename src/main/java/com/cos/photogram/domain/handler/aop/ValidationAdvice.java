package com.cos.photogram.domain.handler.aop;

import com.cos.photogram.domain.handler.ex.CustomApiValidationException;
import com.cos.photogram.domain.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect // AOP 처리를 위한 애노테이션
public class ValidationAdvice {

    @Around("execution( * com.cos.photogram.web.api.*Controller.*(..)") // 전처리 + 후처리
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {

                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }

                    throw new CustomApiValidationException("유효성 검사 실패함", errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }

    @Around("execution( * com.cos.photogram.web.*Controller.*(..)") // 전처리 + 후처리
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }

                    throw new CustomValidationException("유효성 검사 실패함", errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }
}
