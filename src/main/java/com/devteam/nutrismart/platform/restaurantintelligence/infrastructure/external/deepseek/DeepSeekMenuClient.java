package com.devteam.nutrismart.platform.restaurantintelligence.infrastructure.external.deepseek;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class DeepSeekMenuClient {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;
    private final String model;

    public DeepSeekMenuClient(
            @Qualifier("deepSeekMenuRestTemplate") RestTemplate restTemplate,
            @Value("${deepseek.api-key}") String apiKey,
            @Value("${deepseek.base-url}") String baseUrl,
            @Value("${deepseek.model}") String model) {
        this.restTemplate = restTemplate;
        this.apiKey       = apiKey;
        this.baseUrl      = baseUrl;
        this.model        = model;
    }

    public String callDeepSeek(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        var request = new DeepSeekMenuRequest(
                model,
                List.of(new DeepSeekMenuRequest.Message("user", prompt)),
                0.3
        );

        HttpEntity<DeepSeekMenuRequest> entity = new HttpEntity<>(request, headers);
        DeepSeekMenuResponse response = restTemplate.postForObject(
                baseUrl + "/chat/completions", entity, DeepSeekMenuResponse.class);

        if (response == null || response.choices() == null || response.choices().isEmpty()) return "[]";
        return response.choices().get(0).message().content();
    }
}
