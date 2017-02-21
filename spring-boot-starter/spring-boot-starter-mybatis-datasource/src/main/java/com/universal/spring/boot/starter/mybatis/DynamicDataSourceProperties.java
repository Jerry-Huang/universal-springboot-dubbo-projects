package com.universal.spring.boot.starter.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jerryh on 2017/1/18.
 */
@ConfigurationProperties(prefix = DynamicDataSourceProperties.PREFIX)
public class DynamicDataSourceProperties {

    public static final String PREFIX = "mybatis.datasource";

    private String defaultTarget;
    private Map<String, String> url = new HashMap<>();
    private Map<String, String> userName = new HashMap<>();
    private Map<String, String> password = new HashMap<>();
    private Map<String, String> driverClassName = new HashMap<>();

    public String getDefaultTarget() {
        return defaultTarget;
    }

    public void setDefaultTarget(String defaultTarget) {
        this.defaultTarget = defaultTarget;
    }

    public Map<String, String> getUrl() {
        return url;
    }

    public void setUrl(Map<String, String> url) {
        this.url = url;
    }

    public Map<String, String> getUserName() {
        return userName;
    }

    public void setUserName(Map<String, String> userName) {
        this.userName = userName;
    }

    public Map<String, String> getPassword() {
        return password;
    }

    public void setPassword(Map<String, String> password) {
        this.password = password;
    }

    public Map<String, String> getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(Map<String, String> driverClassName) {
        this.driverClassName = driverClassName;
    }
}
