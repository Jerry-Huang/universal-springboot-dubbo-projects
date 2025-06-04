package com.universal.spring.boot.starter.data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = RedisDataProperties.PREFIX)
public class RedisDataProperties {

    public static final String PREFIX = "com.data.redis";

    private String space;

    public String getSpace() {

        return space;
    }

    public void setSpace(String space) {

        this.space = space;
    }

}
