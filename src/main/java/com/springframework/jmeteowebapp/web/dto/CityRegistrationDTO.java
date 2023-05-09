package com.springframework.jmeteowebapp.web.dto;

public class CityRegistrationDTO {
    private String name;
    private String country;
    private String state;
    private String latitude;
    private String longitude;
    private String weatherData;

    public CityRegistrationDTO(){

    }

    public CityRegistrationDTO(String name, String country, String state, String latitude, String longitude, String weatherData) {
        this.name = name;
        this.country = country;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weatherData = weatherData;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getWeatherData() {
        return weatherData;
    }
}
