package com.universal.spring.boot.metadata.exception;

public class NotFoundException extends SystemException {

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
