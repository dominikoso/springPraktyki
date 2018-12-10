package com.example.demo.model;

import com.example.demo.weatherAdds.WeatherDayPeriodEnum;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="weather")
public class WeatherDto implements Serializable {
    private float humidity;

    @Column(name="temperature")
    private float temp;
    @Column(name="pressure")
    private float pressure;

    //@Column(name="weather_id")
    //private Long weather_id;
    @Id
    @Column(name="location_id")
    private Long location_id;
    @Column(name="date")
    private Date date;
    @Column(name="phase_of_day")
    @Enumerated(EnumType.STRING)
    private WeatherDayPeriodEnum weatherDayPeriodEnum;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
//
//    public Long getWeather_id() {
//        return weather_id;
//    }
//
//    public void setWeather_id(Long weather_id) {
//        this.weather_id = weather_id;
//    }

    public Long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Long location_id) {
        this.location_id = location_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public WeatherDayPeriodEnum getWeatherDayPeriodEnum() {
        return weatherDayPeriodEnum;
    }

    public void setWeatherDayPeriodEnum(WeatherDayPeriodEnum weatherDayPeriodEnum) {
        this.weatherDayPeriodEnum = weatherDayPeriodEnum;
    }



}
