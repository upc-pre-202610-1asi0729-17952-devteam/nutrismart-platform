package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.external.deepseek;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DeepSeekCardConfig {

    @Bean
    @Qualifier("deepSeekCardRestTemplate")
    public RestTemplate deepSeekCardRestTemplate() {
        return new RestTemplate();
    }
}
