package com.example.catfoodv1.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用於標記不需要被 LoggingAspect 記錄日誌的類別或方法。
 */
@Target({ElementType.METHOD, ElementType.TYPE}) // 可以標記在方法或類別上
@Retention(RetentionPolicy.RUNTIME) // 在執行期間也保留此 Annotation
public @interface NoLogging {
}