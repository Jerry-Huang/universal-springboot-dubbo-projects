package com.universal.exception;

public class ValidationException extends UniversalException {

    private static final long serialVersionUID = -5353586491332224106L;

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
