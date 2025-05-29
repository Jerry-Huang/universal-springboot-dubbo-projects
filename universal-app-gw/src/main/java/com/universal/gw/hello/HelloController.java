package com.universal.gw.hello;

import com.universal.api.user.service.UserService;
import com.universal.spring.boot.metadata.annotation.Permission;
import com.universal.spring.boot.metadata.annotation.Validation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @DubboReference
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/hello")
    @Permission
    @Cacheable(cacheNames = "TTL30D", key = "'TTL:30D#'+'DISTRICT'")
    @Validation({"$.user-name,required,用户名不能为空,\\d{11},用户名为11位电话号码"})
    public Object hello() throws Exception {

        final Map<String, Object> obj = new HashMap<>();
        obj.put("A", 100);
        obj.put("B", "200B");
        obj.put("C", null);
        obj.put("1", userService.findById(100));
        return obj;
    }
}
