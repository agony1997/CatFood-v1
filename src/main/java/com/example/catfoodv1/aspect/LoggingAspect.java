package com.example.catfoodv1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Value("${logging.slow-query.threshold-ms}")
    private long slowQueryThreshold;

    /**
     * 定義一個切點，目標是所有被 @NoLogging 標記的類別或方法。
     */
    @Pointcut("@within(com.example.catfoodv1.aspect.NoLogging) || @annotation(com.example.catfoodv1.aspect.NoLogging)")
    public void noLoggingAnnotation() {
    }


    @Pointcut("execution(public * com.example.catfoodv1.service..*.*(..))")
    public void serviceLayerExecution() {
    }

    @Pointcut("execution(public * com.soetek.ods.repository..*.*(..))")
    public void repoLayerExecution() {
    }

    @Around("serviceLayerExecution()")
    public Object monitorServiceExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        return proceedAndLog(joinPoint, slowQueryThreshold, "SERVICE");
    }

    @Around("repoLayerExecution()")
    public Object monitorRepoExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        return proceedAndLog(joinPoint, slowQueryThreshold, "REPO");
    }

    private Object proceedAndLog(ProceedingJoinPoint joinPoint, long threshold, String layer) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            // Log and rethrow the exception to not break the application flow.
            logPerformance(joinPoint, startTime, null, throwable, threshold, layer);
            throw throwable;
        }
        logPerformance(joinPoint, startTime, result, null, threshold, layer);
        return result;
    }

    private void logPerformance(ProceedingJoinPoint joinPoint, long startTime, Object result, Throwable throwable, long threshold, String layer) {
        try {
            long duration = System.currentTimeMillis() - startTime;
            String methodName = joinPoint.getSignature().getName();
            String className = joinPoint.getSignature().getDeclaringType().getSimpleName();

            String countInfo = "";

            if (result instanceof java.util.Collection) {
                countInfo = String.format("%d rows,", ((java.util.Collection<?>) result).size());
            } else if (result instanceof Object[]) {
                countInfo = String.format("%d rows,", java.lang.reflect.Array.getLength(result));
            }

            String exceptionInfo = throwable != null ? " with exception: " + throwable.getClass().getSimpleName() : "";

            if (duration > threshold || throwable != null) {
                String callerInfo = findCaller().orElse("");
                log.warn("[SLOW {}] {} ms {} {}.{}() {} {}", layer, duration, countInfo, className, methodName, callerInfo, exceptionInfo);
            } else {
                log.debug("[PERF {}] {} ms {} {}.{}()", layer, duration, countInfo, className, methodName);
            }
        } catch (Exception e) {
            log.error("Error in PerformanceMonitorAspect logging logic.", e);
        }
    }

    private Optional<String> findCaller() {
        return Arrays.stream(Thread.currentThread().getStackTrace())
                // Skip framework internals, proxies, and the aspect itself to find the real application caller.
                .filter(element -> !element.getClassName().equals(LoggingAspect.class.getName())
                        && !element.getClassName().contains("$$")
                        && !element.getClassName().contains("CGLIB")
                        && !element.getClassName().startsWith("java.")
                        && !element.getClassName().startsWith("sun.")
                        && !element.getClassName().startsWith("jdk.")
                        && element.getClassName().startsWith("com.example.catfoodv1"))
                .findFirst()
                .map(element -> String.format("(%s:%d)", element.getFileName(), element.getLineNumber()));
    }
}