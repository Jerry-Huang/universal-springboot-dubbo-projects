package com.universal.spring.boot.starter.dubbo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Jerryh on 2017/2/14.
 */
@ConfigurationProperties(prefix = DubboProperties.PREFIX)
public class DubboProperties {

    public static final String PREFIX = "dubbo";

}
