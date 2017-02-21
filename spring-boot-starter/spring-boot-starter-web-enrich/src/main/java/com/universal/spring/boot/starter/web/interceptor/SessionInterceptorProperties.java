package com.universal.spring.boot.starter.web.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Jerryh on 2017/2/8.
 */
@ConfigurationProperties(prefix = SessionInterceptorProperties.PREFIX)
public class SessionInterceptorProperties {

    public static final String PREFIX = "com.web.session-interceptor";

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    private boolean enable = true;
}
