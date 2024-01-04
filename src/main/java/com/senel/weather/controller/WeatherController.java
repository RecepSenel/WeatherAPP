package com.senel.weather.controller;

import com.senel.weather.validation.CityNameConstraint;
import com.senel.weather.dto.WeatherDto;
import com.senel.weather.service.WeatherService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/app/weather")
@Validated
public class WeatherController {
    private Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {

        this.weatherService = weatherService;
    }


    @GetMapping()
   // @Retry(name = "weather", fallbackMethod = "retryFallback")
    @RateLimiter(name = "weather", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<WeatherDto> getWeatherByCityName(@RequestParam(name = "city") @CityNameConstraint String city){
        logger.info("Weather service Aufruf! ");
        return ResponseEntity.ok(weatherService.getWeatherByCityName(city));
    }

    public ResponseEntity<String> retryFallback(String city, Exception ex){
        logger.info("Retry Fallback Aufruf! ");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Die Anfrage konnte nicht durchgeführt werden!!");
    }

    public ResponseEntity<String> rateLimiterFallback(String city, Exception ex){
        logger.info("Ratelimiter Aufruf!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Das sind zu viele Aufrufe!");
    }

}
