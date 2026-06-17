package com.devteam.nutrismart.platform.restaurantintelligence.infrastructure.external.deepseek;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DeepSeekMenuConfig {

    @Bean
    @Qualifier("deepSeekMenuRestTemplate")
    public RestTemplate deepSeekMenuRestTemplate() {
        return new RestTemplate();
    }
}
