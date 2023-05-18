package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.exception.CityAlreadyAddedException;
import com.springframework.jmeteowebapp.exception.UserNotFoundException;
import com.springframework.jmeteowebapp.model.City;
import com.springframework.jmeteowebapp.model.Users;
import com.springframework.jmeteowebapp.repository.CityRepository;
import com.springframework.jmeteowebapp.repository.UsersRepository;
import com.springframework.jmeteowebapp.web.dto.CityRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
        Users user = usersRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId));

        if (user.getAddedCities().contains(foundCity)) {
            // If the user already has the city, handle it in the controller
            throw new CityAlreadyAddedException("City already added to the user's list.");
        }

        // Update the weatherData of the city
        foundCity.setWeatherData(weatherService.getCityWeather(foundCity.getLat(), foundCity.getLon()));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        foundCity.setUpdated_at(timestamp);
        // add the city to the addedcities of the user
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
