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
@EnableConfigurationProperties(AuthorizationInterceptorProperties.class)
@ComponentScan(basePackages = {"com.universal.spring.boot.starter.web"})
@ConditionalOnProperty(prefix = AuthorizationInterceptorProperties.PREFIX, name = "implement-class", matchIfMissing = false)
public class AuthorizationInterceptorAutoConfiguration extends WebMvcConfigurerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(AuthorizationInterceptorAutoConfiguration.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AuthorizationInterceptorProperties properties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        if (StringUtils.isNotBlank(properties.getImplementClass())) {

            try {
                final Class<?> clazz = Class.forName(properties.getImplementClass());

                if (AbstractAuthorizationInterceptor.class.isAssignableFrom(clazz)) {

                    final AbstractAuthorizationInterceptor authorizationInterceptor = (AbstractAuthorizationInterceptor) clazz.newInstance();

                    // make sure the autowire members have been injected
                    applicationContext.getAutowireCapableBeanFactory().autowireBean(authorizationInterceptor);

                    final InterceptorRegistration registration = registry.addInterceptor(authorizationInterceptor);

                    final List<String> paths = new ArrayList<>();
                    paths.add("/**");
                    registration.addPathPatterns(paths.toArray(new String[paths.size()]));

                    super.addInterceptors(registry);

                    logger.debug("{} {}", authorizationInterceptor.getClass().getName(), StringUtils.join(new String[paths.size()]));
                }else {
                    throw new InstantiationException(String.format("%s does not inherit from AbstractAuthorizationInterceptor", properties.getImplementClass()));
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                logger.error("Register authorization interceptor failed: " + properties.getImplementClass(), e);
            }
        }
    }
}
