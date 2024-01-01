package com.senel.weather.controller;

import com.senel.weather.dto.WeatherDto;
import com.senel.weather.service.WeatherService;
import com.senel.weather.validation.CityNameConstraint;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/app/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping()
    public WeatherDto getWeatherByCityName(@RequestParam(name = "city") @NotBlank @CityNameConstraint String city ){
        return weatherService.getWeatherByCityName(city);
    }
}
