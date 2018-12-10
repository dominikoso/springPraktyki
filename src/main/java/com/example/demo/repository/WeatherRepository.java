package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.WeatherDto;


public interface WeatherRepository extends JpaRepository<WeatherDto, Long>{

}
