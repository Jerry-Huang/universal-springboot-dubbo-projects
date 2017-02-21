package com.universal.spring.boot.starter.web.interceptor;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.universal.session.RedisSession;
import com.universal.session.Session;

/**
 * Created by Jerryh on 2017/2/7.
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(SessionInterceptorProperties.class)
@ComponentScan(basePackages = {"com.universal.spring.boot.starter.web"})
@ConditionalOnProperty(prefix = SessionInterceptorProperties.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
public class SessionInterceptorAutoConfiguration extends WebMvcConfigurerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(SessionInterceptorAutoConfiguration.class);

    @Autowired
    private Session session;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        SessionInterceptor sessionInterceptor = new SessionInterceptor();
        sessionInterceptor.setStorage(session);

        final InterceptorRegistration registration = registry.addInterceptor(sessionInterceptor);

        final List<String> paths = new ArrayList<>();
        paths.add("/**/*.shtml");
        registration.addPathPatterns(paths.toArray(new String[paths.size()]));

        super.addInterceptors(registry);

        logger.debug("{} {}", sessionInterceptor.getClass().getName(), StringUtils.join(new String[paths.size()]));
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public Session sessionClassName(RedisTemplate<String, Serializable> redisTemplate) {

        return new RedisSession(redisTemplate);
    }
}
