package com.universal.spring.boot.starter.web.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Jerryh on 2017/2/8.
 */
@ConfigurationProperties(prefix = ValidationInterceptorProperties.PREFIX)
public class ValidationInterceptorProperties {

    public static final String PREFIX = "com.web.validation-message-interceptor";

    private String implementClass;

    public String getImplementClass() {
        return implementClass;
    }

    public void setImplementClass(String implementClass) {
        this.implementClass = implementClass;
    }
}
