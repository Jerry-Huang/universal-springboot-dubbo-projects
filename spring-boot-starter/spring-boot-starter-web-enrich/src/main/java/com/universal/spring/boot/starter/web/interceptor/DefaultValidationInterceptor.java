package com.universal.spring.boot.starter.web.interceptor;

public class DefaultValidationInterceptor extends AbstractValidationInterceptor {

    @Override
    protected boolean hasLogin(final String token) {
        return true;
    }

}
