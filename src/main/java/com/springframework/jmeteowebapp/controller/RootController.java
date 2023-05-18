package com.springframework.jmeteowebapp.controller;

import com.springframework.jmeteowebapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/")
    public String handleRoot() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !("anonymousUser").equals(authentication.getPrincipal())) {
            return "redirect:/user";
        } else {
            return "redirect:/login";
        }
    }
}