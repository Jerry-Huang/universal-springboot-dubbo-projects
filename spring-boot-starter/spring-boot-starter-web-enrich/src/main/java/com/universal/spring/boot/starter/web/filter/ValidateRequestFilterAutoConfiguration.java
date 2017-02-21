package com.universal.spring.boot.starter.web.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jerryh on 2017/2/9.
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(ValidateRequestFilterProperties.class)
@ComponentScan(basePackages = {"com.universal.spring.boot.starter.web"})
@ConditionalOnProperty(prefix = ValidateRequestFilterProperties.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
public class ValidateRequestFilterAutoConfiguration extends WebMvcConfigurerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(ValidateRequestFilterAutoConfiguration.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ValidateRequestFilterProperties properties;

    @Bean
    public FilterRegistrationBean validateRequestFilter() {

        AbstractValidateRequestFilter validateRequestFilter = new DefaultValidateRequestFilter();

        if (StringUtils.isNotBlank(properties.getImplementClass())) {

            try {
                Class<?> clazz = Class.forName(properties.getImplementClass());

                if (AbstractValidateRequestFilter.class.isAssignableFrom(clazz)) {

                    validateRequestFilter = (AbstractValidateRequestFilter) clazz.newInstance();

                    // make sure the autowire members have been injected
                    applicationContext.getAutowireCapableBeanFactory().autowireBean(validateRequestFilter);
                } else {
                    logger.warn("{} does not inherit from AbstractValidateRequestFilter, use the DefaultValidateRequestFilter.", properties.getImplementClass());
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                logger.error("Instance validate request filter failed: {}", properties.getImplementClass(), e);
            }
        }

        final Map<String, Pair<String, String>> bundles = new HashMap<>();

        for (Map.Entry<String, String> entry : properties.getBundles().entrySet()) {
            final String[] versionDeviceOS = entry.getValue().split(",");
            if (versionDeviceOS.length >= 2) {
                bundles.put(entry.getKey(), new ImmutablePair<String, String>(versionDeviceOS[0], versionDeviceOS[1]));
            }
        }

        validateRequestFilter.setBundles(bundles);

        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(validateRequestFilter);

        final List<String> urls = new ArrayList<>();
        urls.add("*.shtml");
        registrationBean.setUrlPatterns(urls);

        registrationBean.setOrder(2);
        logger.debug("{} {}", registrationBean.getFilter().getClass().getName(), StringUtils.join(urls.toArray(new String[urls.size()])));

        return registrationBean;
    }
}
