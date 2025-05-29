package com.universal.spring.boot.starter.web.filter;

import com.universal.spring.boot.metadata.util.ReflectionUtils;
import com.universal.spring.boot.starter.web.util.UrlPatternUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(ValidateRequestFilterProperties.class)
@ConditionalOnProperty(prefix = ValidateRequestFilterProperties.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
public class ValidateRequestFilterAutoConfiguration implements WebMvcConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(ValidateRequestFilterAutoConfiguration.class);

    private static final int ORDER = 1;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ValidateRequestFilterProperties properties;

    @Bean
    public FilterRegistrationBean validateRequestFilter() throws Exception {

        AbstractValidateRequestFilter validateRequestFilter;
        if (StringUtils.isBlank(properties.getImplementedClass())) {

            validateRequestFilter = new DefaultValidateRequestFilter();
        } else {

            validateRequestFilter = ReflectionUtils.getInstance(properties.getImplementedClass());
            applicationContext.getAutowireCapableBeanFactory().autowireBean(validateRequestFilter);
        }

        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(validateRequestFilter);

        List<String> urlPatterns = UrlPatternUtils.parse(properties.getUrlPatterns());
        if (CollectionUtils.isNotEmpty(urlPatterns)) {

            registrationBean.setUrlPatterns(urlPatterns);
        }

        registrationBean.setOrder(ORDER);
        logger.debug("{} {}", registrationBean.getFilter().getClass(), StringUtils.join(registrationBean.getUrlPatterns()));

        return registrationBean;
    }
}
