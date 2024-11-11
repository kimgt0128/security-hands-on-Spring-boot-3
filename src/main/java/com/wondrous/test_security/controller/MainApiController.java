package com.wondrous.test_security.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class MainApiController {

    @GetMapping
    public String mainPage() {
        return "이 페이지는 누구나 접근할 수 있는 페이지 입니다."; //JSON으로 반환
    }
}
