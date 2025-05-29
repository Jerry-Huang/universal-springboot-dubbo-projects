package com.universal.spring.boot.starter.web.filter;

import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = ValidateRequestFilterProperties.PREFIX)
public class ValidateRequestFilterProperties {

    public static final String PREFIX = "com.web.validate-request-filter";

    private boolean enable = false;
    private String implementedClass;
    private String urlPatterns = Strings.EMPTY;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

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
        this.implementedClass = implementClass;
    }

}
