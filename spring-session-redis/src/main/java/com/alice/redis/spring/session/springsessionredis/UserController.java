package com.alice.redis.spring.session.springsessionredis;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @GetMapping("login")
    public String login(HttpRequest request) {
        
        return "success";
    }

    @GetMapping("get")
    public Object get(HttpServletRequest request) {
        return request.getSession().getAttribute("user");
    }
}
