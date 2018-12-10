package com.example.demo.service;

import com.example.demo.json.Weather;
import com.example.demo.model.WeatherDto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public interface WeatherService {
    public JSONObject getWeather (Long cityId) throws IOException, JSONException;
    public void update (WeatherDto weatherDto);
}
