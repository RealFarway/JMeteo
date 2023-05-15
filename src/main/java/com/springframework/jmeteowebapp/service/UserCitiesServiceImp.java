package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.City;
import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.repository.CityRepository;
import com.springframework.jmeteowebapp.repository.UsersRepository;
import com.springframework.jmeteowebapp.web.dto.CityRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCitiesServiceImp implements UserCitiesService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;
    @Autowired
    private WeatherService weatherService;

    @Override
    public City addCityToUser(Long userId, String city, String country, String state, String lat, String lon) {
        // Find the city
        City foundCity = cityRepository.findFirstByNameAndCountry(city, country);

        if (foundCity == null) {
            // If the city is not found, try to find it via lat and lon
            foundCity = cityRepository.findFirstByLatAndLon(lat, lon);
            if (foundCity == null) {
                // if the city is not found, create a new record on the DB
                CityRegistrationDTO registrationDTO = new CityRegistrationDTO(city, country, state, lat, lon, "");
                String JSON_weather = weatherService.getCityWeather(lat, lon);
                registrationDTO.setWeather(JSON_weather);
                foundCity = cityService.save(registrationDTO);
            }
        }

        // Find the user
        Users user = usersRepository.findById(userId).orElse(null);

        if (user == null) {
            // If the user is not found, handle it in the controller
            // TODO: User case where this can happen
            return null;
        }

        if (user.getAddedCities().contains(foundCity)) {
            // If the user already has the city, handle it in the controller
            // TODO: return a specific error for this case
            return null;
        }

        // Add the city to the user's addedCities
        user.addedCities.add(foundCity);

        // Save the updated user
        usersRepository.save(user);

        return foundCity;
    }

    @Override
    public boolean removeCityFromUser(Long userId, Long cityId) {
        Optional<Users> user = usersRepository.findById(userId);
        if (user.isPresent()) {
            Users foundUser = user.get();
            foundUser.getAddedCities().removeIf(city -> city.getId() == cityId);
            usersRepository.save(foundUser);
            return true;
        } else {
            return false;
        }
    }
}
