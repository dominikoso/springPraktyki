package com.example.demo;

import com.example.demo.dto.BookDto;
import com.example.demo.model.Book;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@EnableJpaRepositories(basePackages = "com.example.demo.repository")
@EntityScan(basePackages = "com.example.demo.model")
public class Test {
    public static void main(String[] args){
        RestTemplate restTemplate = new RestTemplate();

            BookDto response = restTemplate.getForObject("http://localhost:8080/test/random?id=2" , BookDto.class);
            if (response != null){
                System.out.println(response.getName());
            }else {
                System.out.println("Not Found");
            }

    }
}
