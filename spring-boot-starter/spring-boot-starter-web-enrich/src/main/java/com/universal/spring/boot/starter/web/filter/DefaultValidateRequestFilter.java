package com.universal.spring.boot.starter.web.filter;

import javax.servlet.ServletRequest;

import com.universal.exception.ValidationException;

/**
 * Created by Jerryh on 2017/2/9.
 */
public class DefaultValidateRequestFilter extends AbstractValidateRequestFilter {

    private static final long serialVersionUID = -5547709868488174726L;

    @Override
    protected void validate(ServletRequest request) throws ValidationException {

    }
}
