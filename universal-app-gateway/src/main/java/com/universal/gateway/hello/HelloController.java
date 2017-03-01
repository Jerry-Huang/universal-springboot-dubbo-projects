package com.universal.gateway.hello;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.universal.annotation.Authorization;
import com.universal.annotation.Validation;
import com.universal.api.user.service.UserService;

/**
 * Created by Jerryh on 2017/1/9.
 */

@RestController
public class HelloController {

    @Reference
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/hello.shtml")
    //@Cacheable(value = "TTL30D", key = "'DISTRICT'")
    @Authorization
    @Validation({ "$.user-name,required,用户名不能为空,\\d{11},用户名为11位电话号码" })
    public Object hello() throws Exception {

        final Map<String, Object> obj = new HashMap<>();
        obj.put("A", 100);
        obj.put("B", "200B");
        obj.put("C", null);
        obj.put("1", userService.findById(100));
        return obj;
    }
}
