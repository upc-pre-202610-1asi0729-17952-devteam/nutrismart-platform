package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.deepseek;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class DeepSeekClient {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;
    private final String model;

    public DeepSeekClient(@Qualifier("deepSeekRestTemplate") RestTemplate restTemplate,
                          @Value("${deepseek.api-key}") String apiKey,
                          @Value("${deepseek.base-url}") String baseUrl,
                          @Value("${deepseek.model}") String model) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.model = model;
    }

    public String callDeepSeek(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        var request = new DeepSeekEnrichmentRequest(
                model,
                List.of(new DeepSeekEnrichmentRequest.Message("user", prompt)),
                0.3
        );

        HttpEntity<DeepSeekEnrichmentRequest> entity = new HttpEntity<>(request, headers);
        DeepSeekEnrichmentResponse response = restTemplate.postForObject(
                baseUrl + "/chat/completions", entity, DeepSeekEnrichmentResponse.class);

        if (response == null || response.choices() == null || response.choices().isEmpty()) return "[]";
        return response.choices().get(0).message().content();
    }

    public String enrichFoodsBatch(List<String> foodNames) {
        String prompt = buildPrompt(foodNames);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        var request = new DeepSeekEnrichmentRequest(
                model,
                List.of(new DeepSeekEnrichmentRequest.Message("user", prompt)),
                0.2
        );

        HttpEntity<DeepSeekEnrichmentRequest> entity = new HttpEntity<>(request, headers);
        DeepSeekEnrichmentResponse response = restTemplate.postForObject(
                baseUrl + "/chat/completions", entity, DeepSeekEnrichmentResponse.class);

        if (response == null || response.choices() == null || response.choices().isEmpty()) return "[]";
        return response.choices().get(0).message().content();
    }

    private String buildPrompt(List<String> foodNames) {
        String namesJson = "[" + String.join(", ", foodNames.stream()
                .map(n -> "\"" + n.replace("\"", "\\\"") + "\"")
                .toList()) + "]";

        return """
                Dado este array de alimentos en inglés (nombres en formato USDA con comas y descripciones técnicas), para cada uno devuelve JSON con:
                - nameEn: nombre natural en inglés sin comas ni formato USDA, máximo 5 palabras, conciso y claro (ej: "Whole Milk 3.25%%" en vez de "Milk, whole, 3.25%% milkfat, with added vitamin D"; "Ground Beef 80/20" en vez de "Beef, ground, 80%% lean meat / 20%% fat, raw")
                - nameEs: traducción natural al español
                - category: categoría (Grain, Protein, Dairy, Vegetable, Fruit, Fat, Beverage, Other)
                - itemType: INGREDIENT o DISH
                - restrictions: array con los valores de [LACTOSE_FREE, GLUTEN_FREE, VEGAN, VEGETARIAN, NUT_FREE, SEAFOOD_FREE, KOSHER, HALAL] que el alimento CUMPLE (es apto para ese perfil dietético). Un alimento cumple una restricción si NO contiene los ingredientes prohibidos para ese perfil. Ejemplos: el pollo crudo cumple LACTOSE_FREE, GLUTEN_FREE, NUT_FREE, SEAFOOD_FREE (no contiene lácteos, gluten, frutos secos ni mariscos). El queso NO cumple LACTOSE_FREE ni VEGAN. Una hamburguesa de res NO cumple VEGAN ni VEGETARIAN. El salmón NO cumple SEAFOOD_FREE ni VEGAN ni VEGETARIAN.
                - weatherTypes: array de [HOT, COLD, MILD] donde este alimento es apropiado
                - originCountry: país de origen del alimento o plato en inglés (ej: "Italy" para lasagna, "Japan" para sushi, "Peru" para ceviche). Para ingredientes genéricos sin origen cultural específico que provienen de la base de datos USDA, usar "United States"
                - originCity: ciudad de origen específica en inglés si aplica (ej: "Naples" para pizza napolitana, "Bologna" para mortadela), o null si no tiene ciudad de origen específica

                Alimentos: %s

                Responde SOLO con el JSON array, sin texto adicional.""".formatted(namesJson);
    }
}
