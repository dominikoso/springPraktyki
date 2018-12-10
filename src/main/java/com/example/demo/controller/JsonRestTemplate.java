package com.example.demo.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import com.example.demo.model.Book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class JsonRestTemplate {
    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;


    public JsonRestTemplate(){
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        restTemplate.getMessageConverters().add(6, new Jaxb2RootElementHttpMessageConverter());
        for (HttpMessageConverter a :restTemplate.getMessageConverters()) {
            System.out.println("MESSAGE CONVRTER=" + a.toString());
        }

    }
    private <T> ResponseEntity<T> exchange(String url, HttpMethod httpMethod, HttpEntity<?> requestEntity, Class<T> responseType) {
        return restTemplate.exchange(url, httpMethod, requestEntity, responseType);
    }
    public <T> T getForObject(String url, Class<T> responseType) {
        final ResponseEntity<T> responseEntity = exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(httpHeaders), responseType);
        return responseEntity.getBody();
    }
    public <T> T postForObject(String url, Object request, Class<T> responseType) throws IOException {
        return postForObject(url, (new ObjectMapper()).writeValueAsString(request), responseType, null);
    }

    public <T> T postForObject(String url, Class<T> responseType) throws IOException {
        return postForObject(url, new HttpEntity<byte[]>(httpHeaders), responseType, null);
    }

    public <T> T postForObject(String url, String request, Class<T> responseType) throws IOException {
        return postForObject(url, request, responseType, null);
    }

    public <T> T postForObject(String url, Object request, Class<T> responseType, HttpHeaders responseHeaders) throws IOException {
        return postForObject(url, (new ObjectMapper()).writeValueAsString(request), responseType, responseHeaders);
    }

    public <T> T postForObject(String url, String request, Class<T> responseType, HttpHeaders responseHeaders) throws IOException {
        HttpEntity<String> response = exchange(url, HttpMethod.POST, new HttpEntity<>(request, httpHeaders), String.class);
        if (responseHeaders != null) {
            responseHeaders.putAll(response.getHeaders());
        }
        return (new ObjectMapper()).readValue(response.getBody(), responseType);
    }
    public <T> T putForObject(String url, Object request, Class<T> responseType) throws IOException {
        return putForObject(url, request, responseType, null);
    }

    public <T> T putForObject(String url, Object request, Class<T> responseType, HttpHeaders responseHeaders) throws IOException {
        return putForObject(url, (new ObjectMapper()).writeValueAsString(request), responseType, responseHeaders);
    }

    public <T> T putForObject(String url, String request, Class<T> responseType, HttpHeaders responseHeaders) throws IOException {
        HttpEntity<String> response = exchange(url, HttpMethod.PUT, new HttpEntity<>(request, httpHeaders), String.class);
        if (responseHeaders != null) {
            responseHeaders.putAll(response.getHeaders());
        }
        return (new ObjectMapper()).readValue(response.getBody(), responseType);
    }
    public ResponseEntity<?> post(String url, Object request) {
        return exchange(url, HttpMethod.POST, new HttpEntity<>(request, httpHeaders), String.class);
    }
    public ResponseEntity<?> patch(String url, Object request) {
        return exchange(url, HttpMethod.PATCH, new HttpEntity<>(request, httpHeaders), String.class);
    }

}
