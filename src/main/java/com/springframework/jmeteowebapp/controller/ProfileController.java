package com.springframework.jmeteowebapp.controller;

import com.springframework.jmeteowebapp.exception.UnauthorizedAccessException;
import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/profile", "/profile/"})
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public String getUserData(@PathVariable("userId") String userId, Model model, Principal principal) {
        Long parsedUserId;

        try {
            parsedUserId = Long.parseLong(userId);
            Optional<Users> userOptional = userService.getUserById(parsedUserId);
            Users loggedInUser = userService.loadUserByUsername(principal.getName());

            if (userOptional.isPresent()) {
                Users user = userOptional.get();

                // Check if the logged-in user's ID matches the userId in the URL
                if (!loggedInUser.getId().equals(parsedUserId)) {
                    throw new UnauthorizedAccessException();
                }

                model.addAttribute("user", user);
                return "profile";
            } else {
                throw new UnauthorizedAccessException();
            }
        } catch (NumberFormatException e) {
            throw new UnauthorizedAccessException();
        }
    }

    @PostMapping("/update")
    public String updateUserData(@ModelAttribute("user") Users user, Principal principal) {
        Users loggedInUser = userService.loadUserByUsername(principal.getName());

        // Check if the logged-in user's ID matches the userId in the URL
        if (!loggedInUser.getId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }

        userService.update(user);
        return "redirect:/profile/" + user.getId();
    }

    private String getRedirectUrl(Principal principal) {
        Users loggedInUser = userService.loadUserByUsername(principal.getName());
        return "redirect:/profile/" + loggedInUser.getId();
    }

    @GetMapping
    public String redirectToMyProfile(Principal principal) {
        return getRedirectUrl(principal);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorizedAccessException(Principal principal) {
        return getRedirectUrl(principal);
    }
}
