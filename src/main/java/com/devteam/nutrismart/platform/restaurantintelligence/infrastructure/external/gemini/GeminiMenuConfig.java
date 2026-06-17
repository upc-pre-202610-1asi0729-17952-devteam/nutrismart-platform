package com.devteam.nutrismart.platform.restaurantintelligence.infrastructure.external.gemini;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeminiMenuConfig {

    @Bean
    @Qualifier("geminiMenuRestTemplate")
    public RestTemplate geminiMenuRestTemplate() {
        return new RestTemplate();
    }
}
