package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.usda;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class USDAFoodDataConfig {

    @Bean
    @Qualifier("usdaRestTemplate")
    public RestTemplate usdaRestTemplate() {
        return new RestTemplate();
    }
}
