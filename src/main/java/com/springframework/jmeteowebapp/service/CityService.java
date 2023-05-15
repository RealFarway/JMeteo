package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.City;
import com.springframework.jmeteowebapp.web.dto.CityRegistrationDTO;
import org.springframework.stereotype.Service;

@Service
public interface CityService {
    City save(CityRegistrationDTO registrationDTO);
    City addCityToUser(Long userId, String city, String country, String state, String lat, String lon);
}
