package com.springframework.jmeteowebapp.service;

import com.springframework.jmeteowebapp.model.City;
import com.springframework.jmeteowebapp.repository.CityRepository;
import com.springframework.jmeteowebapp.web.dto.CityRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class CityServiceImp implements CityService {
    @Autowired
    private CityRepository cityRepository;

    @Override
    public City save(CityRegistrationDTO registrationDTO) {
        City city = new City(registrationDTO.getName(), registrationDTO.getCountry(), registrationDTO.getLat(), registrationDTO.getLon(), registrationDTO.getWeather(), new Timestamp(System.currentTimeMillis()));
        return cityRepository.save(city);
    }

    @Override
    public Optional<City> loadCityById(Long cityId) {
        return cityRepository.findById(cityId);
    }
}
