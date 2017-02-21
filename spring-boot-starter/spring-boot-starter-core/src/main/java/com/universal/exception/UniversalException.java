package com.universal.exception;

import org.apache.commons.lang3.StringUtils;

public class UniversalException extends Exception {

    private static final long serialVersionUID = -5411785480261545811L;

    private Code code;
    private String debug;

    public UniversalException() {}

    public UniversalException(final String message) {
        super(message);
    }

    public UniversalException(Throwable exception) {
        super(exception);
    }

    public UniversalException(final Code code) {
        this(code, code.getText(), code.getText());
    }

    public UniversalException(final Code code, final String message) {
        this(code, message, code.getText());
    }

    public UniversalException(final Code code, final String message, final String debug) {
        super(message);
        this.code = code;
        this.debug = debug;
    }

    public UniversalException(final String message, final Throwable exception) {
        super(message, exception);
    }

    public UniversalException(final Code code, final Throwable exception) {
        this(code, code.getText(), exception);
    }

    public UniversalException(final Code code, final String message, final Throwable exception) {
        this(code, message, exception, code.getText());
    }

    public UniversalException(final Code code, final String message, final Throwable exception, final String debug) {
        super(message, exception);
        this.code = code;
        this.debug = debug;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    @Override
    public String toString() {

        return StringUtils.isNotBlank(this.debug) ? (super.toString() + ": " + this.debug) : super.toString();
    }
}
