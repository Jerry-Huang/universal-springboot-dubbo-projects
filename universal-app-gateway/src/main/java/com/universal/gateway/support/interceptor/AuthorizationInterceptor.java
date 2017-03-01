package com.universal.gateway.support.interceptor;

import com.universal.spring.boot.starter.web.interceptor.AbstractAuthorizationInterceptor;

public class AuthorizationInterceptor extends AbstractAuthorizationInterceptor {

//    @Autowired
//    private UserMapper userMapper;

    @Override
    protected boolean hasPermission(String token, String uri) {

        return true;
    }
}
