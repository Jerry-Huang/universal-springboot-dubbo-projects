package com.universal.spring.boot.starter.web.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jerryh on 2017/2/9.
 */
@ConfigurationProperties(prefix = ValidateRequestFilterProperties.PREFIX)
public class ValidateRequestFilterProperties {

    public static final String PREFIX = "com.web.validate-request-filter";

    private boolean enable = false;
    private String implementClass;
    private Map<String, String> bundles = new HashMap<>();

    public Map<String, String> getBundles() {
        return bundles;
    }

    public void setBundles(Map<String, String> bundles) {
        this.bundles = bundles;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getImplementClass() {
        return implementClass;
    }

    public void setImplementClass(String implementClass) {
        this.implementClass = implementClass;
    }

}
