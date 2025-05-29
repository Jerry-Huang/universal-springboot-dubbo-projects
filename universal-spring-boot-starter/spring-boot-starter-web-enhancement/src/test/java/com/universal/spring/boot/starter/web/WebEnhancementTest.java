package com.universal.spring.boot.starter.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebEnhancementTest {

    private final static Logger logger = LoggerFactory.getLogger(WebEnhancementTest.class);

    public static void main(String[] args) {

        SpringApplication.run(WebEnhancementTest.class, args);
    }
}
