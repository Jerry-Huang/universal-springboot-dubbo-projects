package com.universal.spring.boot.starter.web.filter;


import com.universal.spring.boot.metadata.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultValidateRequestFilter extends AbstractValidateRequestFilter {

    @Override
    protected void validate(HttpServletRequest request) throws ValidationException {

    }
}
