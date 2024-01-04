package com.senel.weather.service;

import com.senel.weather.data.ResponseData;
import com.senel.weather.dto.WeatherDto;
import com.senel.weather.model.WeatherModel;
import com.senel.weather.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.senel.weather.constants.Constants.*;

@Service
public class WeatherService {

    private final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;

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

    }

    public Optional<WeatherModel> getWeatherByWeatherStack(String city){
        ResponseData result = restTemplate.getForObject(createUrl(city), ResponseData.class);

        return result != null ? Optional.of(saveWeatherModel(result)) : Optional.empty();

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
       return API_URL+ ACCESS_KEY + API_KEY + QUERY + city;
    }

}
