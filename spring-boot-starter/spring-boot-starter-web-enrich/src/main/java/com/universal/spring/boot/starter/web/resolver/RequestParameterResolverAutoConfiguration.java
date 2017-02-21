package com.universal.spring.boot.starter.web.resolver;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by Jerryh on 2017/2/7.
 */
@Configuration
@ConditionalOnWebApplication
@ComponentScan(basePackages = {"com.universal.spring.boot.starter.web"})
public class RequestParameterResolverAutoConfiguration extends WebMvcConfigurerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(RequestParameterResolverAutoConfiguration.class);

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        final RequestParameterMethodArgumentResolver requestParameterResolver = new RequestParameterMethodArgumentResolver();
        argumentResolvers.add(requestParameterResolver);

        logger.debug(requestParameterResolver.getClass().getName());
    }
}
