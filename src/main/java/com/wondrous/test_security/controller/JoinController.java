package com.wondrous.test_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    @GetMapping("/join")
    public String joinPage() {
        return "/join";
    }

    @PostMapping("/joinProc")
    public String joinProcess() {
        //회원 가입 완료 시 로그인 페이지로 리디렉팅
        return "redirect:/login";
    }
}
