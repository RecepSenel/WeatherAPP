package com.senel.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senel.weather.constants.Constants;
import com.senel.weather.data.ResponseData;
import com.senel.weather.dto.WeatherDto;
import com.senel.weather.model.WeatherModel;
import com.senel.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.senel.weather.constants.Constants.*;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
   // private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherService(WeatherRepository repository, RestTemplate restTemplate){
        this.weatherRepository = repository;
        this.restTemplate = restTemplate;
    }

    public WeatherDto getWeatherByCityName(String city){
        Optional<WeatherModel> weatherModel = weatherRepository.findFirstByCityNameOrderByTimeDesc(city);
        if(weatherModel.isPresent()){
            if(weatherModel.get().getLocalDateTime().isBefore(LocalDateTime.now().minusMinutes(30))){
                return getWeatherByWeatherStack(city).map(WeatherDto::convert).get();
            }
            return weatherModel.map(WeatherDto::convert).get();
        }else{
            return getWeatherByWeatherStack(city).map(WeatherDto::convert).get();
        }

        /*
        return (WeatherDto) weatherModel.map(model -> {
            if (model.getLocalDateTime().isBefore(LocalDateTime.now().minusMinutes(30))) {
                return getWeatherByWeatherStack(city).map(WeatherDto::convert);
            } else {
                return WeatherDto.convert(model);
            }
        }).orElseGet(() -> getWeatherByWeatherStack(city).map(WeatherDto::convert));

         */

    }

    public Optional<WeatherModel> getWeatherByWeatherStack(String city){

     //   ResponseEntity<String> result = restTemplate.getForEntity(createUrl(city), String.class);
        ResponseData result = restTemplate.getForObject(createUrl(city), ResponseData.class);

        if(result != null){
            // ResponseData response = objectMapper.readValue(result.getBody(), ResponseData.class);
            return Optional.of(saveWeatherModel(result));
        }
        return Optional.empty();
    }

    private WeatherModel saveWeatherModel(ResponseData responseData){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
        WeatherModel model = new WeatherModel(
                responseData.current().temperature(),
                responseData.location().name(),
                responseData.location().country(),
                LocalDateTime.now()
        );
        return weatherRepository.save(model);
    }

    public String createUrl(String city){
       return API_URL + ACCESS_KEY + API_KEY + QUERY + city;
    }
}
