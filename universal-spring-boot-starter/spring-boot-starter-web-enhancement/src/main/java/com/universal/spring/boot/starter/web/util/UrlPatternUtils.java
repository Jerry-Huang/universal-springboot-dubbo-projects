package com.universal.spring.boot.starter.web.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UrlPatternUtils {

    public static List<String> parse(String urlPatterns) {

        if (StringUtils.isNotBlank(urlPatterns)) {

            String[] urls = StringUtils.split(urlPatterns, ",");

            if (ArrayUtils.isNotEmpty(urls)) {

                return Arrays.stream(urls).map(url -> url.trim()).toList();
            }
        }

        return Collections.EMPTY_LIST;
    }
}
