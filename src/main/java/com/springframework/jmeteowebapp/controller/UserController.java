package com.springframework.jmeteowebapp.controller;

import com.springframework.jmeteowebapp.model.City;
import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.service.CityService;
import com.springframework.jmeteowebapp.service.UserCitiesService;
import com.springframework.jmeteowebapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/user", "/user/"})
public class UserController {

    @Autowired
    private UserCitiesService userCitiesService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CityService cityService;

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

    @PostMapping("/addCity")
    public String addCityToUserDashboard(@RequestParam("cityName") String cityName, @RequestParam("cityLat") String lat, @RequestParam("cityLon") String lon, Principal principal, Model model) {
        Users loggedInUser = userService.loadUserByUsername(principal.getName());
        City city = cityService.addCityToUser(loggedInUser.getId(), cityName, null, null, lat, lon);

        if (city == null) {
            model.addAttribute("errorMessage", "City not found.");
        } else {
            model.addAttribute("successMessage", "City added successfully.");
        }

        return "redirect:/user";
    }

    @GetMapping
    public String redirectToMyDashboard(Principal principal) {
        Users loggedInUser = userService.loadUserByUsername(principal.getName());
        return "redirect:/user/" + loggedInUser.getId();
    }
}