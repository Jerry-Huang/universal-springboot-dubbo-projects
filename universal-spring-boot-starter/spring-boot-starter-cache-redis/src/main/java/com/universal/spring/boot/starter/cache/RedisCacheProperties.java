package com.universal.spring.boot.starter.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = RedisCacheProperties.PREFIX)
public class RedisCacheProperties {

    public static final String PREFIX = "com.cache.redis";
}
