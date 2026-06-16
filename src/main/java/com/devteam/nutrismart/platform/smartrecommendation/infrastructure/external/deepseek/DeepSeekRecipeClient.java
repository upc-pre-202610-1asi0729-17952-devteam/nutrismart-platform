package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.external.deepseek;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DeepSeekRecipeClient {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;
    private final String model;

    public DeepSeekRecipeClient(@Qualifier("deepSeekRecipeRestTemplate") RestTemplate restTemplate,
                                @Value("${deepseek.api-key}") String apiKey,
                                @Value("${deepseek.base-url}") String baseUrl,
                                @Value("${deepseek.model}") String model) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.model = model;
    }

    public String generateRecipesBatch(Map<String, List<String>> ingredientsByCategory,
                                       UserGoal goalType,
                                       int count) {
        String prompt = buildPrompt(ingredientsByCategory, goalType, count);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        var request = new DeepSeekRecipeRequest(
                model,
                List.of(new DeepSeekRecipeRequest.Message("user", prompt)),
                0.7
        );

        HttpEntity<DeepSeekRecipeRequest> entity = new HttpEntity<>(request, headers);
        DeepSeekRecipeResponse response = restTemplate.postForObject(
                baseUrl + "/chat/completions", entity, DeepSeekRecipeResponse.class);

        if (response == null || response.choices() == null || response.choices().isEmpty()) return "[]";
        return response.choices().get(0).message().content();
    }

    private String buildPrompt(Map<String, List<String>> ingredientsByCategory,
                                UserGoal goalType,
                                int count) {
        String ingredientSection = ingredientsByCategory.entrySet().stream()
                .map(e -> "- " + e.getKey() + ": " + String.join(", ", e.getValue()))
                .collect(Collectors.joining("\n"));

        String goalDescription = switch (goalType) {
            case WEIGHT_LOSS -> "WEIGHT_LOSS (recipes should be low-calorie, high-fiber, satisfying)";
            case MUSCLE_GAIN -> "MUSCLE_GAIN (recipes should be high-protein, moderate-carb, calorie-dense)";
        };

        return """
                You are a nutritionist. Generate exactly %d recipes for the following goal.

                AVAILABLE INGREDIENTS BY CATEGORY (use the exact nameKey strings as shown):
                %s

                GOAL: %s

                STRICT RULES:
                - Use ONLY the ingredient nameKeys listed above — do not invent or add any others
                - Each recipe must use between 3 and 8 ingredients
                - nameKeys are lowercase alphanumeric (e.g. "chickenbreast", "brownrice", "spinach")
                - estimatedCalories must be > 0

                Return ONLY a valid JSON array with exactly %d objects, no extra text, no markdown:
                [
                  {
                    "name": "Recipe Name in English",
                    "nameEs": "Nombre natural de la receta en español",
                    "prepTimeMinutes": <integer>,
                    "estimatedCalories": <double>,
                    "estimatedProtein": <double>,
                    "estimatedCarbs": <double>,
                    "estimatedFat": <double>,
                    "ingredients": ["namekey1", "namekey2", ...]
                  }
                ]""".formatted(count, ingredientSection, goalDescription, count);
    }
}
