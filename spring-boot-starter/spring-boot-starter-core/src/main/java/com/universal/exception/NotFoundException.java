package com.universal.exception;

public class NotFoundException extends UniversalException {

    private static final long serialVersionUID = 4091465232123693535L;

    public NotFoundException(final String message) {
        super(message);
    }

    public NotFoundException(Throwable exception) {
        super(exception);
    }

    public NotFoundException(final Code code) {
        super(code);
    }

    public NotFoundException(final Code code, final String message) {
        super(code, message);
    }
}
