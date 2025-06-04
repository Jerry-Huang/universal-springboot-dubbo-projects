package com.universal.spring.boot.starter.data.redis.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class RedisDataControllerTest {

    private static final Logger log = LoggerFactory.getLogger(RedisDataControllerTest.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/")
    public String index() {

        String text = "Hello, Page Index!";
        log.info(text);

        redisTemplate.opsForValue().set("INDEX-PAGE", text, Duration.ofSeconds(1000));

        return text;
    }

}
