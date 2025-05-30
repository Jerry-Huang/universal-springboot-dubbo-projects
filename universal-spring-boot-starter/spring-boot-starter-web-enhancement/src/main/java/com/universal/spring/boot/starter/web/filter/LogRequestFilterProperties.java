package com.universal.spring.boot.starter.web.filter;

import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = LogRequestFilterProperties.PREFIX)
public class LogRequestFilterProperties {

    public static final String PREFIX = "com.web.log-request-filter";


    private boolean enable = true;
    private String urlPatterns = "/*";

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
}
