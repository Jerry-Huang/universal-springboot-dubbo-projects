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

import java.util.List;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(ValidationInterceptorProperties.class)
@ConditionalOnProperty(prefix = ValidationInterceptorProperties.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
public class ValidationInterceptorAutoConfiguration implements WebMvcConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(ValidationInterceptorAutoConfiguration.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ValidationInterceptorProperties properties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        AbstractValidationInterceptor validationInterceptor;
        if (StringUtils.isBlank(properties.getImplementedClass())) {

            validationInterceptor = new DefaultValidationInterceptor();
        } else {

            validationInterceptor = ReflectionUtils.getInstance(properties.getImplementedClass());
            applicationContext.getAutowireCapableBeanFactory().autowireBean(validationInterceptor);
        }

        List<String> urlPatterns = UrlPatternUtils.parse(properties.getUrlPatterns());
        registry.addInterceptor(validationInterceptor).addPathPatterns(urlPatterns);

        logger.debug("{} {}", validationInterceptor.getClass(), properties.getUrlPatterns());
    }
}
