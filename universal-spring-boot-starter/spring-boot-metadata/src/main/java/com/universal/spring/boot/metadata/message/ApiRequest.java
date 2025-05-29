package com.universal.spring.boot.metadata.message;

import java.io.IOException;
import java.io.Serializable;

public class ApiRequest implements Serializable {

    private String raw;

    public ApiRequest() {

    }

    public ApiRequest(final String json) throws IOException {

        setRaw(json);
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    @Override
    public String toString() {

        return raw;
    }
}
