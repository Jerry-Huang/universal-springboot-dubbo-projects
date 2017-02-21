package com.universal.exception;

public class UnexpectedHttpStatusException extends Exception {

    private static final long serialVersionUID = -5681705231764676614L;

    private int statusCode = 0;

    public UnexpectedHttpStatusException(int statusCode) {
        super();
        this.statusCode = statusCode;
    }

    public UnexpectedHttpStatusException(int statusCode, final String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public UnexpectedHttpStatusException(int statusCode, final String message, final Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
