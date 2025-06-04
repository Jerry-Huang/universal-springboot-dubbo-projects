package com.universal.spring.boot.metadata.message;

public class Headers {

    public static final String PREFIX = "X-";

    public static final String SIGN  = PREFIX + "sign";
    public static final String ONCE = PREFIX + "once";
    public static final String TIMESTAMP = PREFIX + "timestamp";
    public static final String AUTH_TOKEN = PREFIX + "auth-token";
    public static final String SOURCE_APPLICATION_VERSION = PREFIX + "source-application-version";
    public static final String SOURCE_DEVICE_ID = PREFIX + "source-device-id";
}
