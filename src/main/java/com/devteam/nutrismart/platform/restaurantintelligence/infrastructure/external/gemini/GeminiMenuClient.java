package com.devteam.nutrismart.platform.restaurantintelligence.infrastructure.external.gemini;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class GeminiMenuClient {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;
    private final String model;

    public GeminiMenuClient(
            @Qualifier("geminiMenuRestTemplate") RestTemplate restTemplate,
            @Value("${gemini.api-key}") String apiKey,
            @Value("${gemini.base-url}") String baseUrl,
            @Value("${gemini.model}") String model) {
        this.restTemplate = restTemplate;
        this.apiKey       = apiKey;
        this.baseUrl      = baseUrl;
        this.model        = model;
    }

    public String generateContent(String prompt, String imageBase64) {
        String cleanBase64 = stripDataUrlPrefix(imageBase64);

        var request = new GeminiMenuRequest(List.of(
                new GeminiMenuRequest.Content(List.of(
                        GeminiMenuRequest.Part.textPart(prompt),
                        GeminiMenuRequest.Part.imagePart(cleanBase64)
                ))
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = baseUrl + "/v1beta/models/" + model + ":generateContent?key=" + apiKey;
        HttpEntity<GeminiMenuRequest> entity = new HttpEntity<>(request, headers);

        GeminiMenuResponse response = restTemplate.postForObject(url, entity, GeminiMenuResponse.class);

        if (response == null || response.candidates() == null || response.candidates().isEmpty()) return "[]";
        var parts = response.candidates().get(0).content().parts();
        if (parts == null || parts.isEmpty()) return "[]";
        return parts.get(0).text();
    }

    private String stripDataUrlPrefix(String base64) {
        if (base64 == null) return "";
        int commaIdx = base64.indexOf(',');
        return commaIdx >= 0 ? base64.substring(commaIdx + 1) : base64;
    }
}
