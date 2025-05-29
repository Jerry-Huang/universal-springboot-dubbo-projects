package com.universal.spring.boot.metadata.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


public class HttpUtils {

    public static final int URL_ENCODE = 1;
    public static final int JS_URL_ENCODE = 2;

    public static String generateQueryString(final Map<String, String> map) {

        return generateQueryString(map, -1);
    }

    public static String generateQueryString(final Map<String, String> map, final int urlEncodeMode) {

        String queryString = StringUtils.EMPTY;
        for (Map.Entry<String, String> entry : map.entrySet()) {

            queryString += entry.getKey() + "=";

            if (urlEncodeMode >= URL_ENCODE && StringUtils.isNotEmpty(entry.getValue())) {

                String encodedValue = StringUtils.EMPTY;

                try {
                    encodedValue = URLEncoder.encode(entry.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                }

                if (urlEncodeMode == JS_URL_ENCODE) {
                    encodedValue = encodedValue.replaceAll("\\+", "%20").replaceAll("\\%21", "!").replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")")
                            .replaceAll("\\%7E", "~");
                }

                queryString += encodedValue;

            } else {
                queryString += entry.getValue();
            }

            queryString += "&";
        }

        return StringUtils.removeEnd(queryString, "&");
    }

    public static String ip(final HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }

        ip = request.getHeader("X-Real-IP");

        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        } else {
            return request.getRemoteAddr();
        }
    }
}
