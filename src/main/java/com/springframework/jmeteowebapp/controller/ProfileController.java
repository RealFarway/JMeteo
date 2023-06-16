package com.springframework.jmeteowebapp.controller;

import com.springframework.jmeteowebapp.exception.UnauthorizedAccessException;
import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.service.UserService;
import com.springframework.jmeteowebapp.web.dto.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String updateUserData(@ModelAttribute("user") UserRegistrationDTO user, Principal principal, RedirectAttributes redirectAttributes) {
        Users loggedInUser = userService.loadUserByUsername(principal.getName());

        try {
            loggedInUser.setName(user.getName());
            loggedInUser.setSurname(user.getSurname());

            userService.updateUser(loggedInUser);
        } catch (Exception e) {
            // Log the exception or do something else
            redirectAttributes.addFlashAttribute("errorMessage", "User details could not be updated.");
            return "redirect:/profile/" + loggedInUser.getId() + "?error";
        }

        redirectAttributes.addFlashAttribute("successMessage", "User details successfully updated.");
        return "redirect:/profile/" + loggedInUser.getId() + "?success";
    }

    @PostMapping("/update-password")
    public String updateUserPassword(
            @RequestParam("current-password") String currentPassword,
            @RequestParam("new-password") String newPassword,
            @RequestParam("confirm-password") String confirmPassword,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        Users loggedInUser = userService.loadUserByUsername(principal.getName());
        try {
            userService.updatePassword(loggedInUser, currentPassword, newPassword, confirmPassword);
            redirectAttributes.addFlashAttribute("successMessage", "Password successfully updated.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/profile/" + loggedInUser.getId() + "?error#password-change";
        }

        return "redirect:/profile/" + loggedInUser.getId() + "?success#password-change";
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
