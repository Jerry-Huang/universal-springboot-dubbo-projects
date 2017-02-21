package com.universal.spring.boot.starter.cache.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jerryh on 2017/2/6.
 */
@ConfigurationProperties(prefix = RedisCacheProperties.PREFIX)
public class RedisCacheProperties {

    public static final String PREFIX = "com.redis.cache";

    private Map<String, Long> expires = new HashMap<>();

    public Map<String, Long> getExpires() {
        return expires;
    }

    public void setExpires(Map<String, Long> expires) {
        this.expires = expires;
    }
}
