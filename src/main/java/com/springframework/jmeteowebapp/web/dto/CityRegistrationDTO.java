package com.springframework.jmeteowebapp.web.dto;

public class CityRegistrationDTO {
    private String name;
    private String country;
    private String state;
    private String lat;
    private String lon;
    private String weather;

    public CityRegistrationDTO(){

    }

    public CityRegistrationDTO(String name, String country, String state, String lat, String lon, String weatherData) {
        this.name = name;
        this.country = country;
        this.state = state;
        this.lat = lat;
        this.lon = lon;
        this.weather = weatherData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
