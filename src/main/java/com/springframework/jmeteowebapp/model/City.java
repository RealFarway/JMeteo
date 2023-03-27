package com.springframework.jmeteowebapp.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "City")
public class City {
    @Id
    @Column(name = "city_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String region;
    private String location;
    @Column(name = "weather")
    private String weatherData;
    private Timestamp updated_at;

    public City(String name, String region, String location, String weatherData, Timestamp updated_at) {
        this.name = name;
        this.region = region;
        this.location = location;
        this.weatherData = weatherData;
        this.updated_at = updated_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(String weatherData) {
        this.weatherData = weatherData;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
