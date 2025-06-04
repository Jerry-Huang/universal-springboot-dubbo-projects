package com.universal.spring.boot.starter.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universal.spring.boot.metadata.Environment;
import com.universal.spring.boot.metadata.exception.Code;
import com.universal.spring.boot.metadata.exception.ValidationException;
import com.universal.spring.boot.metadata.message.ApiResponse;
import com.universal.spring.boot.metadata.message.Headers;
import com.universal.spring.boot.metadata.util.HttpUtils;
import com.universal.spring.boot.metadata.util.VersionUtils;
import com.universal.spring.boot.starter.web.CachedBodyHttpServletRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public abstract class AbstractValidateRequestFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(AbstractValidateRequestFilter.class);

    private static final Set<Environment> IGNORE_ENVIRONMENTS = Set.of(Environment.WORKSTATION, Environment.DEV);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (CollectionUtils.containsAny(IGNORE_ENVIRONMENTS, Environment.current())) {

            filterChain.doFilter(request, response);
            return;
        }

        try {
            this.validateParameter(request);
            this.validateVersion(request);
            this.validateSign(request);

            this.validate(request);

            filterChain.doFilter(request, response);
        } catch (ValidationException e) {

            logger.error("Illegal request, " + e.getMessage(), e);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(e.getCode().getCode());

            if (Environment.current() != Environment.PRD) {
                apiResponse.setDebug(e.getDebug());
            }

            apiResponse.setMessage(e.getMessage());

            response.reset();

            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        }
    }

    protected abstract void validate(final HttpServletRequest request) throws ValidationException;

    private void validateSign(final HttpServletRequest request) throws ValidationException {

        CachedBodyHttpServletRequestWrapper cachedBodyRequest = (CachedBodyHttpServletRequestWrapper) request;

        final String sign = StringUtils.defaultString(cachedBodyRequest.getHeader(Headers.SIGN));

        final Map<String, String> parameterMap = new TreeMap<>(String::compareTo);

        Map<String, String[]> requestParameters = cachedBodyRequest.getParameterMap();
        requestParameters.forEach((key, values) -> parameterMap.put(key, StringUtils.join(values, ",")));

        Iterator<String> headerNames = cachedBodyRequest.getHeaderNames().asIterator();
        headerNames.forEachRemaining(name -> {
            if (StringUtils.startsWithIgnoreCase(name, Headers.PREFIX) && !name.equalsIgnoreCase(Headers.SIGN)) {
                parameterMap.put(name, cachedBodyRequest.getHeader(name));
            }
        });

        final String queryString = HttpUtils.generateQueryString(parameterMap) + "&" + cachedBodyRequest.getBodyString();
        logger.debug("Unsigned: {}", queryString);

        final String signed = DigestUtils.md5Hex(queryString);
        logger.debug("Signature: {}", signed);

        if (!signed.equalsIgnoreCase(sign)) {
            throw new ValidationException(Code.SYS_INVALID_SIGN, String.format("签名校验失败, Unsigned: %s, sign = %s, but expected sign = %s", queryString, sign, signed));
        }
    }

    private void validateVersion(final HttpServletRequest request) throws ValidationException {

        final String version = request.getHeader(Headers.SOURCE_APPLICATION_VERSION);

        if (VersionUtils.isBefor2_1(version)) {
            throw new ValidationException(Code.SYS_INCOMPATIBLE_VERSION, String.format("当前版本 %s 已过期，请升级到最新版本。", version));
        }
    }

    private void validateParameter(final HttpServletRequest request) throws ValidationException {

        final String sign = StringUtils.defaultString(request.getHeader(Headers.SIGN));
        final String once = StringUtils.defaultString(request.getHeader(Headers.ONCE));
        final String timestamp = StringUtils.defaultString(request.getHeader(Headers.TIMESTAMP));
        final String sourceDeviceId = StringUtils.defaultString(request.getHeader(Headers.SOURCE_DEVICE_ID));
        final String sourceApplicationVersion = StringUtils.defaultString(request.getHeader(Headers.SOURCE_APPLICATION_VERSION));

        if (StringUtils.isBlank(sign)) {
            throw new ValidationException(Code.SYS_ILLEGAL_ARGUMENT, Headers.SIGN + "不能为空");
        } else if (StringUtils.isBlank(sourceDeviceId)) {
            throw new ValidationException(Code.SYS_ILLEGAL_ARGUMENT, Headers.SOURCE_DEVICE_ID + "不能为空");
        } else if (StringUtils.isBlank(sourceApplicationVersion)) {
            throw new ValidationException(Code.SYS_ILLEGAL_ARGUMENT, Headers.SOURCE_APPLICATION_VERSION + "不能为空");
        } else if (StringUtils.isBlank(once)) {
            throw new ValidationException(Code.SYS_ILLEGAL_ARGUMENT, Headers.ONCE + "不能为空");
        } else if (StringUtils.isBlank(timestamp)) {
            throw new ValidationException(Code.SYS_ILLEGAL_ARGUMENT, Headers.TIMESTAMP + "不能为空");
        }
    }
}
