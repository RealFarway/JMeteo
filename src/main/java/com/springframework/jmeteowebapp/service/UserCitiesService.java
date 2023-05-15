package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.City;
import com.springframework.jmeteowebapp.model.Users;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserCitiesService {
    City addCityToUser(Long userId, String city, String country, String state, String lat, String lon);
    boolean removeCityFromUser(Long userId, Long cityId);
}