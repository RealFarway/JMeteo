package com.springframework.jmeteowebapp.controller;

import com.springframework.jmeteowebapp.service.UserService;
import com.springframework.jmeteowebapp.web.dto.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/registration", "/registration/"})
public class RegistrationController {
    @Autowired
    private UserService userService;

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
        //Check if username already exists
        if (userService.loadUserByUsername(registrationDTO.getUsername()) != null) {
            return "redirect:/registration?error";
        }
        //if not, save user
        userService.save(registrationDTO);
        return "redirect:/login?success";
    }
}
