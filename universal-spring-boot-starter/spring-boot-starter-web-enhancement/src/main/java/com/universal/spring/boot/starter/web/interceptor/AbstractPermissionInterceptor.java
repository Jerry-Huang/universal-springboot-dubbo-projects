package com.universal.spring.boot.starter.web.interceptor;

import com.universal.spring.boot.metadata.annotation.Permission;
import com.universal.spring.boot.metadata.exception.Code;
import com.universal.spring.boot.metadata.exception.ValidationException;
import com.universal.spring.boot.metadata.message.Headers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;


public abstract class AbstractPermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {

            return true;
        }

        final Method method = ((HandlerMethod) handler).getMethod();

        if (!method.isAnnotationPresent(Permission.class)) {

            return true;
        }

        final String uri = request.getRequestURI();
        final String token = request.getHeader(Headers.AUTH_TOKEN);

        if (!hasPermission(token, uri)) {
            throw new ValidationException(Code.SYS_NO_PERMISSION, "无权限访问 URL = " + uri);
        }

        return true;
    }

    protected abstract boolean hasPermission(final String token, final String uri);
}
