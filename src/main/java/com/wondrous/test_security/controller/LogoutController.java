package com.wondrous.test_security.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

@Slf4j

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("Session Attributes: {}", Collections.list(request.getSession().getAttributeNames()));
        log.info("Session ID: {}", request.getSession().getId());
        log.info("Request URI: {}", request.getRequestURI());
        log.info("Request Method: {}", request.getMethod());
        log.info("Request IP: {}", request.getRemoteAddr());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
             log.info("Logging out user: {}", authentication.getName());
             new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        log.info(String.valueOf(response.getStatus()));
        return "redirect:/login";
    }
}
