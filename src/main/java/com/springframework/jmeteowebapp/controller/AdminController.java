package com.springframework.jmeteowebapp.controller;

import com.springframework.jmeteowebapp.exception.UnauthorizedAccessException;
import com.springframework.jmeteowebapp.model.Role;
import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.service.RoleService;
import com.springframework.jmeteowebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/admin", "/admin/"})
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/{userId}")
    public String getAdminData(@PathVariable("userId") String userId, Model model, Principal principal) {
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

                // Get all users from the database
                model.addAttribute("users", userService.getAllUsers());
                model.addAttribute("currentUserId", loggedInUser.getId());

                return "admin";
            } else {
                throw new UnauthorizedAccessException();
            }
        } catch (NumberFormatException e) {
            throw new UnauthorizedAccessException();
        }
    }

    @PostMapping("/updateRoles/{userId}")
    public String updateUserRoles(@PathVariable("userId") String userId,
                                  @RequestParam("roles") List<String> roles,
                                  Principal principal) {
        Long parsedUserId;

        try {
            parsedUserId = Long.parseLong(userId);
            Users loggedInUser = userService.loadUserByUsername(principal.getName());

            // Allow only an admin or super admin to update a user
            if (loggedInUser.hasRole("ROLE_ADMIN") || loggedInUser.hasRole("ROLE_SUPER_ADMIN")) {
                Optional<Users> editedUser = userService.getUserById(parsedUserId);

                if (editedUser.isPresent()) {
                    List<Role> roleObjects = roles.stream()
                            .map(roleService::loadRoleByName)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(Collectors.toList());
                    userService.updateRoles(editedUser.get(), roleObjects);
                    return "redirect:/admin/" + loggedInUser.getId() + "?success";
                }

            } else {
                throw new UnauthorizedAccessException();
            }
        } catch (NumberFormatException e) {
            throw new UnauthorizedAccessException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin";
    }

    @PostMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable("userId") String userId, Principal principal) {
        Long parsedUserId;

        try {
            parsedUserId = Long.parseLong(userId);
            Users loggedInUser = userService.loadUserByUsername(principal.getName());

            // Allow only an admin or super admin to delete a user
            if (loggedInUser.hasRole("ROLE_ADMIN") || loggedInUser.hasRole("ROLE_SUPER_ADMIN")) {
                userService.deleteUser(parsedUserId);
                return "redirect:/admin";
            } else {
                throw new UnauthorizedAccessException();
            }
        } catch (NumberFormatException e) {
            throw new UnauthorizedAccessException();
        }

    }

    private String getRedirectUrl(Principal principal) {
        Users loggedInUser = userService.loadUserByUsername(principal.getName());
        return "redirect:/admin/" + loggedInUser.getId();
    }

    @GetMapping
    public String redirectToMyAdminPage(Principal principal) {
        return getRedirectUrl(principal);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorizedAccessException(Principal principal) {
        return getRedirectUrl(principal);
    }

}
