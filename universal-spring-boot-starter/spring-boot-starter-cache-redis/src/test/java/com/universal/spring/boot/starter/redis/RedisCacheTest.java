package com.universal.spring.boot.starter.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RedisCacheTest {

    private final static Logger logger = LoggerFactory.getLogger(RedisCacheTest.class);

    public static void main(String[] args) {

        SpringApplication.run(RedisCacheTest.class, args);
    }
}
