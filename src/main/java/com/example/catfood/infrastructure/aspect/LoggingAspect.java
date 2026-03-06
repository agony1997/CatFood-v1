package com.example.catfood.infrastructure.aspect;

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

    @Pointcut("@within(com.example.catfood.infrastructure.aspect.NoLogging) || @annotation(com.example.catfood.infrastructure.aspect.NoLogging)")
    public void noLoggingAnnotation() {
    }

    @Pointcut("execution(public * com.example.catfood.application..*.*(..))")
    public void serviceLayerExecution() {
    }

    @Around("serviceLayerExecution() && !noLoggingAnnotation()")
    public Object monitorServiceExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        return proceedAndLog(joinPoint, slowQueryThreshold, "SERVICE");
    }

    private Object proceedAndLog(ProceedingJoinPoint joinPoint, long threshold, String layer) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
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
                .filter(element -> !element.getClassName().equals(LoggingAspect.class.getName())
                        && !element.getClassName().contains("$$")
                        && !element.getClassName().contains("CGLIB")
                        && !element.getClassName().startsWith("java.")
                        && !element.getClassName().startsWith("sun.")
                        && !element.getClassName().startsWith("jdk.")
                        && element.getClassName().startsWith("com.example.catfood"))
                .findFirst()
                .map(element -> String.format("(%s:%d)", element.getFileName(), element.getLineNumber()));
    }
}
