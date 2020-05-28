package com.liao.spring.security.jwt.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liao
 * @since 2020/5/28 15:29
 */
@RestController
public class DemoController {

    @GetMapping("/user")
    public String user() {

        return "user";
    }

    @PostMapping("/user/add")
    public String userAdd() {
        return "userAdd";
    }


    @DeleteMapping("/user/delete")
    public String userDelete() {
        return "userDelete";
    }

    @DeleteMapping("/demo1/11")
    public String demo() {
        return "demo*";
    }
}
