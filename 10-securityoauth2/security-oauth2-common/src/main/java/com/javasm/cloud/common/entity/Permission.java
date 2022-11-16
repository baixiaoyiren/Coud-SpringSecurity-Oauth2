package com.javasm.cloud.common.entity;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-11-16:49
 * Description: 权限注解
 * @author admin
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Permission {
    MyAuthentication[] value() default MyAuthentication.QUERY;
}
