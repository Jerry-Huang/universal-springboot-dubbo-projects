package com.universal.spring.boot.starter.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisCacheControllerTest {

    private static final Logger log = LoggerFactory.getLogger(RedisCacheControllerTest.class);

    @RequestMapping("/")
    @Cacheable(cacheNames = "test", key = "'TTL:1D#'+'INDEXPAGE'")
    public String index() {

        String text = "Hello, Page Index!";
        log.info(text);
        return text;
    }

}
