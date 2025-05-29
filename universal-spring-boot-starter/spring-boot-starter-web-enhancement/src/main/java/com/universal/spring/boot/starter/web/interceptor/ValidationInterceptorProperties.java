package com.universal.spring.boot.starter.web.interceptor;

import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = ValidationInterceptorProperties.PREFIX)
public class ValidationInterceptorProperties {

    public static final String PREFIX = "com.web.validation-message-interceptor";

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

    public void setImplementedClass(String implementClass) {

        this.implementedClass = implementedClass;
    }
}
