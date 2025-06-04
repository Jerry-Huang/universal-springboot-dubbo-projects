package com.universal.spring.boot.starter.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.Assert;

import java.time.Duration;

public class CustomizedTtlFunction implements RedisCacheWriter.TtlFunction {

    private final static String DURATION_PREFIX = "P";

    @Override
    public Duration getTimeToLive(Object key, Object value) {

        Assert.notNull(key, "Cache key must not be null");

        String cacheKey = key.toString();
        String ttl = TtlExpression.parse(cacheKey);

        if (StringUtils.isBlank(ttl)) {
            return null;
        }

        return Duration.parse(DURATION_PREFIX + ttl);
    }

}
