package com.universal.spring.boot.starter.web.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = PermissionInterceptorProperties.PREFIX)
public class PermissionInterceptorProperties {

    public static final String PREFIX = "com.web.permission-interceptor";

    private String implementedClass;
    private String urlPatterns = "/**";

    public String getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(String urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public String getImplementedClass() {
        return implementedClass;
    }

    public void setImplementedClass(String implementedClass) {
        this.implementedClass = implementedClass;
    }
}
