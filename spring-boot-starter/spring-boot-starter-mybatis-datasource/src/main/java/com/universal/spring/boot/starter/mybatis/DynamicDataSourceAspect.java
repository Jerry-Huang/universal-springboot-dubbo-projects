package com.universal.spring.boot.starter.mybatis;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {

    private final static Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Autowired
    private DynamicDataSourceProperties properties;

    @Before("execution(* com.universal..mapper.*.*(..))")
    public void setDataSource(JoinPoint point) throws Throwable {

        final Object target = point.getTarget();
        final MethodSignature signature = (MethodSignature) point.getSignature();

        final String methodName = signature.getName();
        final Class<?>[] clazzes = target.getClass().getInterfaces();
        final Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();

        try {
            final Method method = clazzes[0].getMethod(methodName, parameterTypes);
            if (method != null) {

                DataSource dataSource = null;

                if (method.isAnnotationPresent(DataSource.class)) {
                    dataSource = method.getAnnotation(DataSource.class);
                    DynamicDataSource.setDataSource(dataSource.value());
                } else if (clazzes[0].isAnnotationPresent(DataSource.class)) {
                    dataSource = clazzes[0].getAnnotation(DataSource.class);
                    DynamicDataSource.setDataSource(dataSource.value());
                } else {
                    DynamicDataSource.setDataSource(properties.getDefaultTarget());
                }

                logger.debug("DataSource = {}", DynamicDataSource.getDataSource());
            }

        } catch (Exception e) {
            logger.error("Cut-in datasource failed.", e);
        }
    }
}
