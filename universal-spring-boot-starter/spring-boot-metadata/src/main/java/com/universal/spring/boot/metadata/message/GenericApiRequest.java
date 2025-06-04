package com.universal.spring.boot.metadata.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericApiRequest<T> extends ApiRequest {

    protected T payload;
    protected ObjectMapper objectMapper = new ObjectMapper();

    public GenericApiRequest() {

    }

    public GenericApiRequest(String json) throws Exception {

        super(json);

        this.payload = objectMapper.readValue(json, new TypeReference<>() {
        });
    }
}
