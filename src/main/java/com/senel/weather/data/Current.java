package com.senel.weather.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Current(
        @JsonProperty("observation_time")
        String observationTime,
         int temperature,
        @JsonProperty("weather_code")
        int weatherCode,
        @JsonProperty("weather_icons")
        List<String> weatherIcons,
        @JsonProperty("weather_descriptions")
        List<String> weatherDescriptions,
        @JsonProperty("wind_speed")
        int windSpeed,
        @JsonProperty("wind_degree")
        int windDegree,
        @JsonProperty("wind_dir")
        String windDir,
        int pressure,
        int precip,
        int humidity,
        int cloudcover,
        int feelslike,
        @JsonProperty("uv_index")
        int uvIndex,
        int visibility,
        @JsonProperty("is_day")
        String isDay
) {
}
