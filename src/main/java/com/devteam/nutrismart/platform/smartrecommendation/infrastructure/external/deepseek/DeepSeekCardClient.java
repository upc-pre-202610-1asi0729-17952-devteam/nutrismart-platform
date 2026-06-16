package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.external.deepseek;

import com.devteam.nutrismart.platform.smartrecommendation.application.ports.FoodItemData;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeepSeekCardClient {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;
    private final String model;

    public DeepSeekCardClient(@Qualifier("deepSeekCardRestTemplate") RestTemplate restTemplate,
                               @Value("${deepseek.api-key}") String apiKey,
                               @Value("${deepseek.base-url}") String baseUrl,
                               @Value("${deepseek.model}") String model) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.model = model;
    }

    public String generateCardsBatch(List<FoodItemData> foods, String cardType, WeatherType weatherType, String travelCity) {
        String prompt = buildPrompt(foods, cardType, weatherType, travelCity);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        var request = new DeepSeekCardRequest(
                model,
                List.of(new DeepSeekCardRequest.Message("user", prompt)),
                0.7
        );

        HttpEntity<DeepSeekCardRequest> entity = new HttpEntity<>(request, headers);
        DeepSeekCardResponse response = restTemplate.postForObject(
                baseUrl + "/chat/completions", entity, DeepSeekCardResponse.class);

        if (response == null || response.choices() == null || response.choices().isEmpty()) return "[]";
        return response.choices().get(0).message().content();
    }

    private String buildPrompt(List<FoodItemData> foods, String cardType, WeatherType weatherType, String travelCity) {
        String foodList = foods.stream()
                .map(f -> "  - id=%d name=\"%s\" nameEs=\"%s\" origin=\"%s, %s\" kcal/100g=%.0f protein/100g=%.1f"
                        .formatted(
                                f.id(),
                                f.name() != null ? f.name() : "",
                                f.nameEs() != null ? f.nameEs() : "",
                                f.originCity() != null ? f.originCity() : "?",
                                f.originCountry() != null ? f.originCountry() : "?",
                                f.caloriesPer100g() != null ? f.caloriesPer100g() : 0.0,
                                f.proteinPer100g() != null ? f.proteinPer100g() : 0.0
                        ))
                .collect(Collectors.joining("\n"));

        String contextInstruction = switch (cardType) {
            case "weather" -> buildWeatherInstruction(weatherType);
            case "travel" -> buildTravelInstruction(travelCity);
            case "preventive" -> """
                    The user has missed several meals recently (AT_RISK adherence).
                    For each food, write an encouraging 2-sentence recommendation motivating the user to eat this
                    nutritious food to get back on track. Be warm and supportive, not clinical.""";
            case "intervention" -> """
                    The user has stopped logging meals for multiple days (DROPPED adherence).
                    For each food, write a brief, gentle 2-sentence message encouraging the user to start
                    with this simple, approachable food as their first step back to healthy eating.""";
            default -> "For each food, write a 2-sentence nutritional recommendation.";
        };

        return """
                You are a nutritionist. Below is a list of food items with their IDs.
                %s

                FOOD ITEMS:
                %s

                STRICT RULES:
                - Return one object per food item in the same order
                - Use the exact foodId integers as provided
                - description: 2 sentences in English
                - descriptionEs: 2 sentences in Spanish
                - badge: one of [restaurant, homemade, seasonal, snack, street-food, local, must-try, comfort-food]
                - estimatedCalories must be > 0

                Return ONLY a valid JSON array with exactly %d objects, no extra text, no markdown:
                [
                  {
                    "foodId": <integer id>,
                    "description": "<2 sentences in English>",
                    "descriptionEs": "<2 sentences in Spanish>",
                    "badge": "<badge value>"
                  }
                ]""".formatted(contextInstruction, foodList, foods.size());
    }

    private String buildWeatherInstruction(WeatherType weatherType) {
        String weatherDesc = switch (weatherType) {
            case HOT -> "HOT weather (above 28°C) — the user needs hydrating, light, cooling foods";
            case COLD -> "COLD weather (below 12°C) — the user needs warming, calorie-dense, comforting foods";
            case MILD -> "MILD weather (12–28°C) — balanced, versatile foods appropriate for pleasant conditions";
            case null -> "current weather conditions";
        };
        return "The user is experiencing " + weatherDesc + ". For each food, write 2 sentences explaining " +
                "why this food is particularly suitable for this weather, referencing its properties or origin.";
    }

    private String buildTravelInstruction(String travelCity) {
        return "The user is visiting " + (travelCity != null ? travelCity : "a new city") + ". " +
                "For each food, write 2 sentences explaining where to find it or why it's worth trying during the visit. " +
                "If the food originates elsewhere (e.g., Italian lasagna in a Latin American city), acknowledge its origin " +
                "and mention it can be found locally at restaurants in " + (travelCity != null ? travelCity : "this city") + ".";
    }
}
