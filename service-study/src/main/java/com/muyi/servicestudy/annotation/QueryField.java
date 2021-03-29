package com.muyi.servicestudy.annotation;

import java.lang.annotation.*;

/**
 * 定义属性是否参与查询
 * @author muyi
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QueryField {
    //字段名
    String name() default "";
    //是否必传
    boolean must() default false;
}
