package com.springframework.jmeteowebapp.controller;

import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.service.UserCitiesService;
import com.springframework.jmeteowebapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/user", "/user/"})
public class UserController {

    @Autowired
    private UserCitiesService userCitiesService;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/{userId}")
    public String getUserCities(@PathVariable("userId") Long userId, Model model, Principal principal) {
        Optional<Users> userOptional = userService.getUserById(userId);
        Users loggedInUser = userService.loadUserByUsername(principal.getName());

        if (userOptional.isPresent()) {
            Users user = userOptional.get();

            // Check if the logged-in user's ID matches the userId in the URL
            if (!loggedInUser.getId().equals(userId)) {
                model.addAttribute("error", "Access denied");
                return "error";
            }

            model.addAttribute("listCities", user.addedCities);
            return "user";
        } else {
            model.addAttribute("error", "User not found");
            return "error";
        }
    }

    @GetMapping
    public String redirectToMyDashboard(Principal principal) {
        Users loggedInUser = userService.loadUserByUsername(principal.getName());
        return "redirect:/user/" + loggedInUser.getId();
    }
}