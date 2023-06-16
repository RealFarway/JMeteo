package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.City;
import com.springframework.jmeteowebapp.web.dto.CityRegistrationDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CityService {
    City save(CityRegistrationDTO registrationDTO);
    Optional<City> loadCityById(Long cityId);
}
