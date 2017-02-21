package com.universal.spring.boot.starter.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

/**
 * Created by Jerryh on 2017/2/6.
 */
@Configuration
@EnableConfigurationProperties(RedisDataProperties.class)
@ComponentScan(basePackages = {"com.universal.spring.boot.starter.data.redis"})
public class RedisDataAutoConfiguration {

    @Autowired
    private RedisDataProperties properties;

    @Bean
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        if (StringUtils.hasText(properties.getKeySerializerPrefix())) {
            final PrefixKeyRedisSerializer prefixKeyRedisSerializer = new PrefixKeyRedisSerializer();
            prefixKeyRedisSerializer.setPrefix(properties.getKeySerializerPrefix());
            redisTemplate.setKeySerializer(prefixKeyRedisSerializer);
        }

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
