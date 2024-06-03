package com.test.bootbegin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test.do")
    public String test() {

        return "안녕하세요. 스프링 부트입니다.";
    }

    @GetMapping("/hello.do")
    public String hello() {

        return "안녕하세요. 스프링 부트입니다.";
    }

}








