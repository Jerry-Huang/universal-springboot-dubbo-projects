package com.universal.spring.boot.starter.web.interceptor;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Jerryh on 2017/2/7.
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(ValidationInterceptorProperties.class)
@ComponentScan(basePackages = {"com.universal.spring.boot.starter.web"})
@ConditionalOnProperty(prefix = ValidationInterceptorProperties.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
public class ValidationInterceptorAutoConfiguration extends WebMvcConfigurerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(ValidationInterceptorAutoConfiguration.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ValidationInterceptorProperties properties;

    @Autowired
    private DefaultValidationInterceptor defaultInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        AbstractValidationInterceptor validationInterceptor = defaultInterceptor;

        if (StringUtils.isNotBlank(properties.getImplementClass())) {

            try {
                Class<?> clazz = Class.forName(properties.getImplementClass());

                if (AbstractValidationInterceptor.class.isAssignableFrom(clazz)) {

                    validationInterceptor = (AbstractValidationInterceptor) clazz.newInstance();

                    // make sure the autowire members have been injected
                    applicationContext.getAutowireCapableBeanFactory().autowireBean(validationInterceptor);
                }else {
                    throw new InstantiationException(String.format("%s does not inherit from AbstractValidationInterceptor", properties.getImplementClass()));
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                logger.error("Register validation interceptor failed: " + properties.getImplementClass(), e);
            }
        }

        final InterceptorRegistration registration = registry.addInterceptor(validationInterceptor);

        final List<String> paths = new ArrayList<>();
        paths.add("/**");
        registration.addPathPatterns(paths.toArray(new String[paths.size()]));

        super.addInterceptors(registry);

        logger.debug("{} {}", validationInterceptor.getClass().getName(), StringUtils.join(new String[paths.size()]));

    }

    @Bean
    @ConditionalOnProperty(prefix = ValidationInterceptorProperties.PREFIX, name = "implement-class", matchIfMissing = true)
    public DefaultValidationInterceptor defaultInterceptor() {

        return new DefaultValidationInterceptor();
    }
}
