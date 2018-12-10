package com.example.demo.service.impl;

import com.example.demo.controller.JsonRestTemplate;
import com.example.demo.model.Book;
import com.example.demo.model.WeatherDto;
import com.example.demo.repository.WeatherRepository;
import com.example.demo.service.WeatherService;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.demo.json.JsonReader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;
import java.io.IOException;
import org.json.JSONException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.OneToOne;

@Service(value="weatherService")
public class WeatherServiceImpl implements WeatherService{

    @Autowired
    WeatherRepository weatherRepository;
    @Autowired
    RestTemplate restTemplate;

    JsonRestTemplate jsonRestTemplate = new JsonRestTemplate();

    @RequestMapping(value="/getweather", method = RequestMethod.GET)
    public JSONObject getWeather (Long cityId) throws IOException, JSONException{
        List<String> results = new ArrayList<>();
        //JSONArray obj = jsonRestTemplate.getForObject("http://api.openweathermap.org/data/2.5/forecast?id="+cityId+"&APPID=be942a017ff05b9cb1075118347221f2", JSONArray.class);
        JSONObject obj = JsonReader.readJsonFromUrl("http://api.openweathermap.org/data/2.5/forecast?id="+cityId+"&APPID=be942a017ff05b9cb1075118347221f2");
        return obj;
    }

    private static boolean isOneToOneRelation(Field property) {
        Annotation[] annotations = property.getDeclaredAnnotations();
        boolean isOneToOne = false;

        for(Annotation annotation : annotations){

            if(annotation instanceof OneToOne){
                isOneToOne = true;
            }
        }

        return isOneToOne;

    }

    public static Object copyNonNullProperties(Object target, Object in) {
        if (in == null || target == null || target.getClass() != in.getClass()) return null;

        final BeanWrapper src = new BeanWrapperImpl(in);
        final BeanWrapper trg = new BeanWrapperImpl(target);

        for (final Field property : target.getClass().getDeclaredFields()) {
            Object providedObject = src.getPropertyValue(property.getName());
            if (isOneToOneRelation(property) || (providedObject != null && !(providedObject instanceof Collection<?>))) {
                trg.setPropertyValue(
                        property.getName(),
                        providedObject);
            }
        }
        return target;
    }

    public void update(WeatherDto weatherDto){
        WeatherDto forUpdate = weatherRepository.findOne(weatherDto.getLocation_id());
        copyNonNullProperties(forUpdate, weatherDto);
        weatherRepository.save(forUpdate);
    }
}
