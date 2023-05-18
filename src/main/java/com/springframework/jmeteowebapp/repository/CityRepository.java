package com.springframework.jmeteowebapp.repository;

import com.springframework.jmeteowebapp.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City findFirstByName(String name);
    City findFirstByNameAndCountry(String name, String country);
    City findById(long city_id);
    City findFirstByLatAndLon(String lat, String lon);
}