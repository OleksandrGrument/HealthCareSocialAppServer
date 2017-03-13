package com.ComeOnBaby.service;

import com.ComeOnBaby.model.City;

import java.util.List;


public interface CityService {

    Long addNewCity(String name);

    City findById(Long id);

    City findByName(String name);

    void updateCity(City city);

    void removeCity(City city);

    List<City> getAllCities();
}
