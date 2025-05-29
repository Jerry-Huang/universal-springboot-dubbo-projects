package com.universal.spring.boot.starter.data.redis.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisDataTest {

    private final static Logger logger = LoggerFactory.getLogger(RedisDataTest.class);

    public static void main(String[] args) {

        SpringApplication.run(RedisDataTest.class, args);
    }
}
