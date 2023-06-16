package com.springframework.jmeteowebapp.controller;

import com.springframework.jmeteowebapp.exception.CityAlreadyAddedException;
import com.springframework.jmeteowebapp.exception.UnauthorizedAccessException;
import com.springframework.jmeteowebapp.exception.UserNotFoundException;
import com.springframework.jmeteowebapp.model.City;
import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.service.CityService;
import com.springframework.jmeteowebapp.service.UserCitiesService;
import com.springframework.jmeteowebapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getUserCities(@PathVariable("userId") String userId, Model model, Principal principal) {
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

                model.addAttribute("listCities", user.addedCities);
                return "user";
            } else {
                throw new UnauthorizedAccessException();
            }
        } catch (NumberFormatException e) {
            throw new UnauthorizedAccessException();
        }

    }

    @PostMapping("/addCity")
    public String addCityToUser(@RequestParam("cityName") String cityName, @RequestParam("cityLat") String lat, @RequestParam("cityLon") String lon, Principal principal, Model model, RedirectAttributes redirectAttrs) {
        Users loggedInUser = userService.loadUserByUsername(principal.getName());

        // Validate input parameters
        if (cityName == null || cityName.isEmpty() || lat == null || lat.isEmpty() || lon == null || lon.isEmpty()) {
            redirectAttrs.addFlashAttribute("errorMessage", "Invalid city data.");
            return "redirect:/user";
        }

        City city = userCitiesService.addCityToUser(loggedInUser.getId(), cityName, null, null, lat, lon);

        if (city == null) {
            redirectAttrs.addFlashAttribute("errorMessage", "City not found.");
        } else {
            redirectAttrs.addFlashAttribute("successMessage", "City added successfully.");
        }

        return "redirect:/user";
    }

    @PostMapping("/deleteCity")
    public String removeCityFromUser(@RequestParam("cityId") Long cityId, Principal principal, Model model, RedirectAttributes redirectAttrs) {

        Users loggedInUser = userService.loadUserByUsername(principal.getName());
        Optional<City> city = cityService.loadCityById(cityId);

        // TODO: Re-do error handling
        if (!city.isPresent()) {
            redirectAttrs.addFlashAttribute("errorMessage", "City not found.");
        } else {
            // Check if the city is in the user's list of cities
            if (loggedInUser.getAddedCities().contains(city.get())) {
                userCitiesService.removeCityFromUser(loggedInUser.getId(), cityId);
                redirectAttrs.addFlashAttribute("successMessage", "City removed successfully.");
            } else {
                redirectAttrs.addFlashAttribute("errorMessage", "You don't have permission to remove this city.");
            }
        }

        return "redirect:/user";
    }

    private String getRedirectUrl(Principal principal) {
        Users loggedInUser = userService.loadUserByUsername(principal.getName());
        return "redirect:/user/" + loggedInUser.getId();
    }

    @GetMapping
    public String redirectToMyDashboard(Principal principal) {
        return getRedirectUrl(principal);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorizedAccessException(Principal principal) {
        return getRedirectUrl(principal);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException ex, Model model, RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/user";
    }

    @ExceptionHandler(CityAlreadyAddedException.class)
    public String handleCityAlreadyAddedException(CityAlreadyAddedException ex, Model model, RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/user";
    }

}