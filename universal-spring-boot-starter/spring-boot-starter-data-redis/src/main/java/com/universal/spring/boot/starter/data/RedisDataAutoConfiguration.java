package com.universal.spring.boot.starter.data;

import io.micrometer.common.util.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableConfigurationProperties(RedisDataProperties.class)
public class RedisDataAutoConfiguration {

    private RedisDataProperties properties;

    public RedisDataAutoConfiguration(RedisDataProperties properties) {

        this.properties = properties;
    }

    @Bean
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        if (StringUtils.isNotBlank(properties.getSpace())) {

            final PrefixKeyRedisSerializer prefixKeyRedisSerializer = new PrefixKeyRedisSerializer();
            prefixKeyRedisSerializer.setPrefix(properties.getSpace());
            redisTemplate.setKeySerializer(prefixKeyRedisSerializer);
        }

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
