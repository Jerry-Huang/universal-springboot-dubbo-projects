package com.universal.spring.boot.starter.web.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Jerryh on 2017/2/9.
 */
@ConfigurationProperties(prefix = LogRequestFilterProperties.PREFIX)
public class LogRequestFilterProperties {

    public static final String PREFIX = "com.web.log-request-filter";

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    private boolean enable = true;

}
