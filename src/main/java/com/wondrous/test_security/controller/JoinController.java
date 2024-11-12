package com.wondrous.test_security.controller;

import com.wondrous.test_security.dto.JoinRequestDto;
import com.wondrous.test_security.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor

@Controller
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/join")
    public String joinPage() {
        return "/join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinRequestDto request) {
        System.out.println(request.getMemberName());
        joinService.join(request);
        //회원 가입 완료 시 로그인 페이지로 리디렉팅
        return "redirect:/login";
    }
}
