package com.example.catfoodv1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    /**
     * 定義一個切點 (Pointcut)，目標是 service 套件及其子套件下的所有公開方法。
     */
    @Pointcut("within(com.example.catfoodv1.service..*)")
    public void serviceMethods() {
    }

    /**
     * 使用環繞通知 (Around advice) 來包裹目標方法的執行。
     */
    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        log.info("====> In  : {}, args = {}", joinPoint.getSignature().toShortString(), Arrays.toString(args));

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // 執行原始方法
        long executionTime = System.currentTimeMillis() - start;

        log.info("<==== Out : Executed in {} ms", executionTime);
        return result;
    }
}