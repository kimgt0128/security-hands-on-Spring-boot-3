package com.wondrous.test_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String index() {
        return "/main";
    }
    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }
}
