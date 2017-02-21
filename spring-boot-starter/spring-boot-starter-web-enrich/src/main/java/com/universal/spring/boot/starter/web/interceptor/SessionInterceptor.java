package com.universal.spring.boot.starter.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.universal.session.Session;
import com.universal.util.SessionUtils;


public class SessionInterceptor extends HandlerInterceptorAdapter {

    private Session storage;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final String token = request.getParameter("token");

        if (StringUtils.isNotBlank(token)) {

            Session session = SessionUtils.current();

            if (session == null) {
                session = storage.clone();
                SessionUtils.current(session);
            }

            session.setId(token);
        }

        return true;
    }

    public void setStorage(final Session storage) {

        this.storage = storage;
    }
}
