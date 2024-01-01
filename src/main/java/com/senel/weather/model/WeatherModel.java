package com.senel.weather.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Time;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "weather")
@Table(name = "weather")
public class WeatherModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name ="UUD", strategy = "org.hibernate.id.UUIDGenerator" )

    private int id;
    private int temperatur;
    private String cityName;
    private String country;
    private LocalDateTime time;

    public WeatherModel(int temperatur, String cityName, String country, LocalDateTime localtime) {
        this.temperatur = temperatur;
        this.cityName = cityName;
        this.country = country;
        this.time = localtime;
    }

    public WeatherModel(int id, int temperatur, String cityName, String country, LocalDateTime localtime) {
        this.id = id;
        this.temperatur = temperatur;
        this.cityName = cityName;
        this.country = country;
        this.time = localtime;
    }

    public WeatherModel() {
    }

    public int getId() {
        return id;
    }

    public int getTemperatur() {
        return temperatur;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public LocalDateTime getLocalDateTime() {
        return time;
    }

    //    private String icon;
//    private String description;
//    private int windSpeed;
//    private int wind_speed;
//    private int wind_degree;
//    private String wind_dir;
//    private int pressure;
//    private int precip;
//    private int humidity;
//    private int cloudcover;
//    private int feelslike;
//    private int uv_index;
//    private int visibility;
//    private boolean is_day;
}
