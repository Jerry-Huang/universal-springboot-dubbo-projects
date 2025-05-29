package com.universal.spring.boot.starter.web.filter;

import com.universal.spring.boot.starter.web.util.UrlPatternUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(LogRequestFilterProperties.class)
@ConditionalOnProperty(prefix = LogRequestFilterProperties.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
public class LogRequestFilterAutoConfiguration implements WebMvcConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(LogRequestFilterAutoConfiguration.class);

    private static final int ORDER = Integer.MIN_VALUE;

    @Autowired
    private LogRequestFilterProperties properties;

    @Bean
    public FilterRegistrationBean<LogRequestFilter> logRequestFilter() {

        final FilterRegistrationBean<LogRequestFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LogRequestFilter());

        List<String> urlPatterns = UrlPatternUtils.parse(properties.getUrlPatterns());

        if (CollectionUtils.isNotEmpty(urlPatterns)) {

            registrationBean.setUrlPatterns(urlPatterns);
        }

        registrationBean.setOrder(ORDER);
        logger.debug("{} {}", registrationBean.getFilter().getClass(), StringUtils.join(registrationBean.getUrlPatterns()));

        return registrationBean;
    }

}
