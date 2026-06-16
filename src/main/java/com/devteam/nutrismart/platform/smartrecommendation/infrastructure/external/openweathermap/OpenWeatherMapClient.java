package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.external.openweathermap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenWeatherMapClient {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;

    public OpenWeatherMapClient(RestTemplate restTemplate,
                                @Value("${openweathermap.api-key}") String apiKey,
                                @Value("${openweathermap.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public OpenWeatherMapCurrentWeatherResponse fetchCurrentWeather(String city, String country) {
        String query = (country != null && !country.isBlank()) ? city + "," + country : city;
        String url = baseUrl + "/weather?q={query}&units=metric&appid={apiKey}";
        return restTemplate.getForObject(url, OpenWeatherMapCurrentWeatherResponse.class, query, apiKey);
    }
}
