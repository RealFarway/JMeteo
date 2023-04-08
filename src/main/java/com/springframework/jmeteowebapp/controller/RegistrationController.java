package com.springframework.jmeteowebapp.controller;

import com.springframework.jmeteowebapp.service.UserService;
import com.springframework.jmeteowebapp.web.dto.UserRegistrationDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private UserService userService;

    public RegistrationController(UserService userService) {
//        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDTO userRegistrationDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDTO registrationDTO) {
        userService.save(registrationDTO);
        //TODO: check if username is unique before trying to save it on DB
        return "redirect:/registration?success";
    }
}
