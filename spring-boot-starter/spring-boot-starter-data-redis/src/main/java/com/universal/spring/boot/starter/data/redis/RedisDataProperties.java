package com.universal.spring.boot.starter.data.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Jerryh on 2017/2/6.
 */
@ConfigurationProperties(prefix = RedisDataProperties.PREFIX)
public class RedisDataProperties {

    public static final String PREFIX = "com.redis.data";

    private String keySerializerPrefix;

    public String getKeySerializerPrefix() {
        return keySerializerPrefix;
    }

    public void setKeySerializerPrefix(String keySerializerPrefix) {
        this.keySerializerPrefix = keySerializerPrefix;
    }

}
