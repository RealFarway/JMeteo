package com.springframework.jmeteowebapp.controller;

import com.springframework.jmeteowebapp.service.UsersService;
import com.springframework.jmeteowebapp.web.dto.UserRegistrationDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private UsersService usersService;

    public RegistrationController(UsersService usersService) {
//        super();
        this.usersService = usersService;
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
        usersService.save(registrationDTO);
        //TODO: check if username is unique before trying to save it on DB
        return "redirect:/registration?success";
    }
}
