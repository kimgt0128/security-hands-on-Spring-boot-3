package com.wondrous.test_security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class MainController {

    @GetMapping("/main")
    public String index(Model model) {
        /**
         * 현재 로그인한 사용자의 정보를 가져오는 메서드
         * 원래 서비스 계층에서 유저의 정보를 가져와야 하지만 편의상 컨트롤러에서 진행
         */
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> Iterator = authorities.iterator();
        GrantedAuthority grantedAuthority = Iterator.next();
        String role = grantedAuthority.getAuthority();

        model.addAttribute("id",id);
        model.addAttribute("role", role);
        return "/main";
    }
}
