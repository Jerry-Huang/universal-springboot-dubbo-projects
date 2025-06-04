package com.universal.spring.boot.starter.web.filter;

import com.universal.spring.boot.metadata.message.Headers;
import com.universal.spring.boot.metadata.util.HttpUtils;
import com.universal.spring.boot.starter.web.CachedBodyHttpServletRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

public class LogRequestFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(LogRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        CachedBodyHttpServletRequestWrapper cachedBodyRequest = new CachedBodyHttpServletRequestWrapper(request);

        if (logger.isInfoEnabled()) {

            final StringBuilder log = new StringBuilder();

            log.append(request.getRequestURI()).append(", ");
            log.append("IP: " + HttpUtils.ip(request));

            log.append(", HEADERS: [");
            Iterator<String> headerNames = request.getHeaderNames().asIterator();
            headerNames.forEachRemaining(name -> {
                if (StringUtils.startsWithIgnoreCase(name, Headers.PREFIX)) {
                    log.append(String.format("%s = %s, ", name, request.getHeader(name)));
                }
            });

            log.append("], QUERIES: [");
            Map<String, String[]> parameters = request.getParameterMap();
            parameters.forEach((key, value) -> log.append(String.format("%s = %s, ", key, StringUtils.join(value, ','))));

            log.append("], BODY: [" + cachedBodyRequest.getBodyString());
            log.append("]");

            logger.info(log.toString());
        }

        filterChain.doFilter(cachedBodyRequest, response);
    }

}
