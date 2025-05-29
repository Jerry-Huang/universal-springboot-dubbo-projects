package com.universal.spring.boot.starter.web.resolver;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@ConditionalOnWebApplication
public class RequestParameterResolverAutoConfiguration implements WebMvcConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(RequestParameterResolverAutoConfiguration.class);

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        final RequestParametertMethodArgumentResolver requestParameterResolver = new RequestParametertMethodArgumentResolver();
        argumentResolvers.add(requestParameterResolver);

        logger.debug(requestParameterResolver.getClass().getName());
    }
}
