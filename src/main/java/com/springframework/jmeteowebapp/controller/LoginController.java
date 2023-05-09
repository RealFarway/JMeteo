package com.springframework.jmeteowebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/login", "/login/"})
public class LoginController {

    @GetMapping
    public String showLoginForm() {
        return "login";
    }

    @PostMapping
    public String login() {
        return "login";
    }
}