package com.senel.weather.dto;


import com.senel.weather.model.WeatherModel;

public record WeatherDto(
        String cityName,
        int temperatur,
        String country
){
    public static WeatherDto convert(WeatherModel from){
        return new WeatherDto(from.getCityName(), from.getTemperatur(), from.getCountry());
    }
}