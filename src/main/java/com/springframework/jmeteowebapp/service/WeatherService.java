package com.springframework.jmeteowebapp.service;

import org.springframework.stereotype.Service;

@Service
public interface WeatherService {
    String getCityWeather(String lat, String lon);
}
