package com.senel.weather.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static String API_URL;
    public static String API_KEY;
    public static String ACCESS_KEY;
    public static String QUERY;

    @Value(("${weather-stack.api.url}"))
    public void SetApiUrl(String apiUrl){
        API_URL = apiUrl;
    }

    @Value(("${weather-stack.api.key}"))
    public void SetApiKey(String apiKey){
        API_KEY = apiKey;
    }

    @Value(("${weather-stack.api.accessKey}"))
    public void SetAccessKey(String accessKey){
        ACCESS_KEY = accessKey;
    }

    @Value(("${weather-stack.api.query}"))
    public void SetQuery(String query){
        QUERY = query;
    }
}
