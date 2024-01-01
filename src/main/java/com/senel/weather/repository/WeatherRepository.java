package com.senel.weather.repository;

import com.senel.weather.model.WeatherModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherModel, String> {

    /*
        select * from entity where cityName = city order by localtime desc limit 1;
     */
    Optional<WeatherModel> findFirstByCityNameOrderByTimeDesc(String city);



}
