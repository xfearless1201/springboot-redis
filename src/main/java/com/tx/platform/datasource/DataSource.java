package com.tx.platform.datasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述:
 * 自定义注解类
 * @Author: Hardy
 * @Date: 2018年12月15日 20:15:03
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSource {
    DataSourceType value();
}
