package com.universal.spring.boot.metadata.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectApiResponse extends GenericApiResponse<Object> {

    public ObjectApiResponse() {

    }

    public ObjectApiResponse(final Object payload) {

        this.setPayload(payload);
    }
}
