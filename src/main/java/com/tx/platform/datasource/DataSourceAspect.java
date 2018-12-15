package com.tx.platform.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *  @ClassName DataSourceAspect
 *  @Description 动态装配数据源aop
 *  @Author Hardy
 *  @Date 2018年12月15日 20:17
 *  @Version 1.0.0
 *  
 **/
@Aspect
@Component
public class DataSourceAspect {

    /**
     * 使用空方法定义切点表达式
     */
    @Pointcut("execution(* com.tx.platform.mapper.*.*(..))")
    public void declareJointPointExpression() {
    }

    /**
     * 使用定义切点表达式的方法进行切点表达式的引入
     */
    @Before("declareJointPointExpression()")
    public void before(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();

        Class<?>[] classz = target.getClass().getInterfaces();

        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
                .getMethod().getParameterTypes();
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource data = m.getAnnotation(DataSource.class);
                DynamicDataSource.setDataSourceType(data.value().getDataBase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
