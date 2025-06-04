package com.universal.spring.boot.metadata.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse implements Serializable {

    private int code;
    private String debug;
    private String message = "OK";

    protected ObjectMapper objectMapper = new ObjectMapper();

    public final static ApiResponse OK = new ApiResponse();

    public ApiResponse() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {

        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {

            return StringUtils.EMPTY;
        }
    }
}
