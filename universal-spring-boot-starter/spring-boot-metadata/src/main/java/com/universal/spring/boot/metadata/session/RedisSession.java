package com.universal.spring.boot.metadata.session;

import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Set;

public class RedisSession extends AbstractSession {

    private RedisTemplate<String, Serializable> redisTemplate;

    private String key;

    private static final String KEY = "SESSION#%s";
    private static final String KEY_EXPIRED = "KEY-EXPIRED";

    public RedisSession(final RedisTemplate<String, Serializable> redisTemplate) {

        super(redisTemplate);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void remove(final String key) {

        redisTemplate.opsForHash().delete(this.key, key);
    }

    @Override
    public void invalidate() {

        redisTemplate.delete(this.key);
    }

    @Override
    public <T extends Serializable> T get(String key, Class<T> clazz) {

        return clazz.cast(redisTemplate.opsForHash().get(this.key, key));
    }

    @Override
    public <T extends Serializable> void put(String key, T value) {

        redisTemplate.opsForHash().put(this.key, key, value);

        if (!redisTemplate.opsForHash().hasKey(this.key, KEY_EXPIRED)) {
            redisTemplate.expire(this.key, timeout, timeUnit);
            redisTemplate.opsForHash().put(this.key, KEY_EXPIRED, timeout);
        }
    }

    @Override
    public void setId(final String id) {
        super.setId(id);
        this.key = String.format(KEY, super.getId());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T get(String key) {

        return (T) redisTemplate.opsForHash().get(this.key, key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<String> keys() {

        return (Set<String>) (Set<?>) redisTemplate.opsForHash().keys(this.key);
    }

}
