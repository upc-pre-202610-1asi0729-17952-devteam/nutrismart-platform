package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.deepseek;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DeepSeekConfig {

    @Bean
    @Qualifier("deepSeekRestTemplate")
    public RestTemplate deepSeekRestTemplate() {
        return new RestTemplate();
    }
}
