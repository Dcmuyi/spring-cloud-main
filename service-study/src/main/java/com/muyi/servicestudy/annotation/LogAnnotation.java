package com.muyi.servicestudy.annotation;

import java.lang.annotation.*;

/**
 * 定义是否日志监控
 * @author muyi
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAnnotation {
    /**
     * 操作名称
     */
    String name() default "";

    /**
     * 说明
     */
    String description() default "";

}
