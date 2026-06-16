package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.external.deepseek;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DeepSeekRecipeConfig {

    @Bean
    @Qualifier("deepSeekRecipeRestTemplate")
    public RestTemplate deepSeekRecipeRestTemplate() {
        return new RestTemplate();
    }
}
