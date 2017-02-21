package com.universal.spring.boot.starter.web.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Jerryh on 2017/2/9.
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(LogRequestFilterProperties.class)
@ComponentScan(basePackages = { "com.universal.spring.boot.starter.web" })
@ConditionalOnProperty(prefix = LogRequestFilterProperties.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
public class LogRequestFilterAutoConfiguration extends WebMvcConfigurerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(LogRequestFilterAutoConfiguration.class);

    @Bean
    public FilterRegistrationBean logRequestFilter() {

        final FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setFilter(new LogRequestFilter());

        final List<String> urls = new ArrayList<>();
        urls.add("*.shtm");
        urls.add("*.shtml");
        registration.setUrlPatterns(urls);

        registration.setOrder(1);
        logger.debug("{} {}", registration.getFilter().getClass().getName(), StringUtils.join(urls.toArray(new String[urls.size()])));

        return registration;
    }
}
