package com.universal.spring.boot.starter.web;

import com.universal.spring.boot.starter.web.interceptor.AbstractPermissionInterceptor;

public class MyPermissionInterceptor extends AbstractPermissionInterceptor {

    @Override
    protected boolean hasPermission(String token, String uri) {

        if ("123".equals(token)) {
            return true;
        }

        return false;
    }
}
