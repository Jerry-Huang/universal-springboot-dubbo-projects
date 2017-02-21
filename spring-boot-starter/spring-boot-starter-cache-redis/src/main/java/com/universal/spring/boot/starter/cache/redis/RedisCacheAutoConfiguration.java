package com.universal.spring.boot.starter.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jerryh on 2017/2/6.
 */
@Configuration
@EnableConfigurationProperties(RedisCacheProperties.class)
@ComponentScan(basePackages = {"com.universal.spring.boot.starter.cache.redis"})
public class RedisCacheAutoConfiguration extends CachingConfigurerSupport {

    private final static Logger logger = LoggerFactory.getLogger(RedisCacheAutoConfiguration.class);

    @Autowired
    private RedisCacheProperties properties;

    @Bean
    public RedisCacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {

        final RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        final Map<String, Long> expires = new HashMap<>();
        expires.put("TTL5S", new Long(5));
        expires.put("TTL1M", new Long(60));
        expires.put("TTL5M", new Long(300));
        expires.put("TTL15M", new Long(900));
        expires.put("TTL30M", new Long(1800));
        expires.put("TTL1H", new Long(3600));
        expires.put("TTL2H", new Long(7200));
        expires.put("TTL5H", new Long(18000));
        expires.put("TTL1D", new Long(86400));

        expires.putAll(properties.getExpires());

        cacheManager.setExpires(expires);

        cacheManager.afterPropertiesSet();

        return cacheManager;
    }

    @Bean
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                logger.error(String.format("%s:%s:%s", cache.getName(), key, exception.getMessage()), exception);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                logger.error(String.format("%s:%s:%s", cache.getName(), key, exception.getMessage()), exception);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                logger.error(String.format("%s:%s:%s", cache.getName(), key, exception.getMessage()), exception);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                logger.error(String.format("%s:%s", cache.getName(), exception.getMessage()), exception);
            }
        };
    }
}
