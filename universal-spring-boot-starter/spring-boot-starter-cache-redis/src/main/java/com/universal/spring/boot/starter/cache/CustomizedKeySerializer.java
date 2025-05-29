package com.universal.spring.boot.starter.cache;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

public class CustomizedKeySerializer extends StringRedisSerializer {

    private final static String TTL_SPLITTER = "#";

    @Override
    public byte[] serialize(String key) {

        Assert.notNull(key, "Cache key must not be null");

        return super.serialize(TtlExpression.remove(key));
    }
}
