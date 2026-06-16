package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.external.openweathermap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenWeatherMapConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
