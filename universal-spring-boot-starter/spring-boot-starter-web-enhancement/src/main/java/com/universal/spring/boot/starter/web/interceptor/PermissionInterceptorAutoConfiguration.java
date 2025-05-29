package com.universal.spring.boot.starter.web.interceptor;


import com.universal.spring.boot.metadata.util.ReflectionUtils;
import com.universal.spring.boot.starter.web.util.UrlPatternUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(PermissionInterceptorProperties.class)
@ConditionalOnProperty(prefix = PermissionInterceptorProperties.PREFIX, name = "implemented-class", matchIfMissing = false)
public class PermissionInterceptorAutoConfiguration implements WebMvcConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(PermissionInterceptorAutoConfiguration.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PermissionInterceptorProperties properties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        if (StringUtils.isBlank(properties.getImplementedClass())) {

            return;
        }

        AbstractPermissionInterceptor permissionInterceptor = ReflectionUtils.getInstance(properties.getImplementedClass());

        // make sure the autowire members have been injected
        applicationContext.getAutowireCapableBeanFactory().autowireBean(permissionInterceptor);

        List<String> urlPatterns = UrlPatternUtils.parse(properties.getUrlPatterns());
        registry.addInterceptor(permissionInterceptor).addPathPatterns(urlPatterns);

        logger.debug("{} {}", permissionInterceptor.getClass(), properties.getUrlPatterns());
    }
}
