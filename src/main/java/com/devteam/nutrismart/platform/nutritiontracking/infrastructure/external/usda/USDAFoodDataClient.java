package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.usda;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class USDAFoodDataClient {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;

    public USDAFoodDataClient(@Qualifier("usdaRestTemplate") RestTemplate restTemplate,
                              @Value("${usda.fooddata.api-key}") String apiKey,
                              @Value("${usda.fooddata.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public USDAFoodSearchResponse searchFoods(String query, int maxResults, String dataType) {
        String url = baseUrl + "/foods/search?api_key=" + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "query", query,
                "dataType", List.of(dataType),
                "pageSize", maxResults
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(url, request, USDAFoodSearchResponse.class);
    }
}
