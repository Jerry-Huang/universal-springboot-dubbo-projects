package com.universal.spring.boot.starter.dubbo;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

/**
 * Created by Jerryh on 2017/2/14.
 */
@Configuration
@EnableConfigurationProperties(DubboProperties.class)
@ComponentScan(basePackages = {"com.universal.spring.boot.starter.dubbo"})
@ImportResource({"classpath*:dubbo.xml", "classpath*:dubbo-*.xml"})
@PropertySources(@PropertySource(value = {"classpath:dubbo.properties", "classpath:dubbo-consumer.properties", "classpath:dubbo-provider.properties"}, ignoreResourceNotFound = true))
public class DubboAutoConfiguration {

}
