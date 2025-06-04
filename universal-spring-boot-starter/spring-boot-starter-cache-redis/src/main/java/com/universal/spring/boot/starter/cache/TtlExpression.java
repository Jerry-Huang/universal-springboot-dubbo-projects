package com.universal.spring.boot.starter.cache;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class TtlExpression {

    private static final String TTL_OPEN = "TTL:";
    private static final String TTL_CLOSE = "#";
    private static final Pattern TTL_PATTERN = Pattern.compile(TTL_OPEN+".*?"+TTL_CLOSE);

    public static String parse(String key) {

        return StringUtils.substringBetween(key, TTL_OPEN, TTL_CLOSE);
    }

    public static String remove(String key) {

        return RegExUtils.removeFirst(key, TTL_PATTERN);
    }
}
