package com.universal.spring.boot.metadata.exception;

public class ValidationException extends SystemException {

    public ValidationException(final String message) {
        super(message);
    }

    public ValidationException(Throwable exception) {
        super(exception);
    }

    public ValidationException(final Code code) {
        super(code);
    }

    public ValidationException(final Code code, final String message) {
        super(code, message);
    }

    public ValidationException(final Code code, final String message, final String debug) {
        super(code, message, debug);
    }
}
