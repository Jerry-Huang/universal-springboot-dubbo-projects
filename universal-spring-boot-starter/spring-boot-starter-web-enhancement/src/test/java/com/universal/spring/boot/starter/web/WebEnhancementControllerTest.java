package com.universal.spring.boot.starter.web;

import com.universal.spring.boot.metadata.annotation.Permission;
import com.universal.spring.boot.metadata.annotation.RequestParameter;
import com.universal.spring.boot.metadata.annotation.Validation;
import com.universal.spring.boot.metadata.message.ApiResponse;
import com.universal.spring.boot.metadata.message.SimpleApiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebEnhancementControllerTest {

    private static final Logger log = LoggerFactory.getLogger(WebEnhancementControllerTest.class);

    @RequestMapping("/")
    @Validation({"$.requiredField,required,requiredField字段必填",
            "$.numberField,\\d+,numberField必须数字"})
    @Permission
    public ApiResponse index(@RequestParameter SimpleApiRequest request) {

        String text = "Hello, Page Index!";
        log.info(text);
        log.info("Request: {}", request);
        log.info("Request Parameter: {}", request.getString("testField"));

        return ApiResponse.OK;
    }

}
