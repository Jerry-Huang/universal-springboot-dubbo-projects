package com.universal.spring.boot.starter.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.universal.annotation.Authorization;
import com.universal.exception.Code;
import com.universal.exception.ValidationException;

public abstract class AbstractAuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {

            return true;
        }

        final Method method = ((HandlerMethod) handler).getMethod();

        if (!method.isAnnotationPresent(Authorization.class)) {

            return true;
        }

        final String uri = request.getRequestURI();
        final String token = request.getParameter("token");

        if (!hasPermission(token, uri)) {
            throw new ValidationException(Code.SYS_NO_PERMISSION, "没有权限");
        }

        return true;
    }

    protected abstract boolean hasPermission(final String token, final String uri);
}
