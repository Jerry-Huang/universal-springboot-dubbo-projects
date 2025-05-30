package com.universal.spring.boot.metadata.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleApiResponse extends GenericApiResponse<Map<String, Object>> {

    public SimpleApiResponse() {

        this.setPayload(new HashMap<>());
    }

    public void put(final String name, final Object value) {

        this.getPayload().put(name, value);
    }
}
