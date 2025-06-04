package com.universal.spring.boot.starter.web.resolver;

import com.universal.spring.boot.metadata.annotation.RequestParameter;
import com.universal.spring.boot.starter.web.CachedBodyHttpServletRequestWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Constructor;

public class RequestParametertMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(RequestParameter.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Class<?> clazz = parameter.getParameterType();
        Constructor<?> constructor = clazz.getConstructor(String.class);

        CachedBodyHttpServletRequestWrapper cachedBodyRequest = webRequest.getNativeRequest(CachedBodyHttpServletRequestWrapper.class);

        return constructor.newInstance(cachedBodyRequest.getBodyString());
    }
}
