package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.deepseek;

import com.devteam.nutrismart.platform.nutritiontracking.application.ports.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DeepSeekMenuRankingAdapter implements MenuRankingPort {

    private static final Logger log = LoggerFactory.getLogger(DeepSeekMenuRankingAdapter.class);

    private final DeepSeekClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DeepSeekMenuRankingAdapter(DeepSeekClient client) {
        this.client = client;
    }

    @Override
    public List<RankedDishData> rankMenuDishes(
            List<MenuDishCandidate> dishes,
            List<FoodItemCandidate> existingMatches,
            UserProfileData profile) {

        if (dishes == null || dishes.isEmpty()) return Collections.emptyList();

        String prompt = buildPrompt(dishes, existingMatches, profile);
        String raw;
        try {
            raw = client.callDeepSeek(prompt);
        } catch (Exception e) {
            log.error("[DEEPSEEK MENU] callDeepSeek failed: {}", e.getMessage(), e);
            return buildFallback(dishes);
        }
        log.info("[DEEPSEEK MENU RAW] {}", raw);
        String json = stripMarkdown(raw);

        try {
            List<JsonNode> nodes = objectMapper.readValue(json, new TypeReference<>() {});
            List<RankedDishData> results = new ArrayList<>();
            for (JsonNode node : nodes) {
                GeneratedMenuFoodData generated = null;
                JsonNode gfNode = node.get("generatedFoodData");
                if (gfNode != null && !gfNode.isNull()) {
                    generated = new GeneratedMenuFoodData(
                            textOrNull(gfNode, "nameEn"),
                            textOrNull(gfNode, "nameEs"),
                            textOrDefault(gfNode, "category", "Other"),
                            textOrDefault(gfNode, "itemType", "DISH"),
                            toStringList(gfNode.get("restrictions")),
                            toStringList(gfNode.get("weatherTypes")),
                            textOrNull(gfNode, "originCity"),
                            textOrDefault(gfNode, "originCountry", "Unknown"),
                            doubleOrDefault(gfNode, "caloriesPer100g", 150.0),
                            doubleOrDefault(gfNode, "proteinPer100g", 8.0),
                            doubleOrDefault(gfNode, "carbsPer100g", 20.0),
                            doubleOrDefault(gfNode, "fatPer100g", 5.0),
                            doubleOrDefault(gfNode, "fiberPer100g", 2.0),
                            doubleOrDefault(gfNode, "sugarPer100g", 3.0),
                            doubleOrDefault(gfNode, "servingSize", 250.0),
                            textOrDefault(gfNode, "servingUnit", "g")
                    );
                }

                String dishNameEs = generated != null ? generated.nameEs() : null;

                results.add(new RankedDishData(
                        textOrDefault(node, "dishName", "Unknown dish"),
                        dishNameEs,
                        textOrNull(node, "price"),
                        textOrNull(node, "matchedNameKey"),
                        generated,
                        doubleOrDefault(node, "compatibilityScore", 50.0),
                        textOrDefault(node, "reason", ""),
                        textOrDefault(node, "reasonEn", ""),
                        doubleOrDefault(node, "estimatedCalories", 300.0),
                        doubleOrDefault(node, "estimatedProtein", 15.0),
                        doubleOrDefault(node, "estimatedCarbs", 30.0),
                        doubleOrDefault(node, "estimatedFat", 10.0)
                ));
            }
            return results;
        } catch (Exception e) {
            log.error("[DEEPSEEK MENU] Failed to parse response: {}", e.getMessage());
            return buildFallback(dishes);
        }
    }

    private String buildPrompt(List<MenuDishCandidate> dishes, List<FoodItemCandidate> existingMatches, UserProfileData profile) {
        StringBuilder sb = new StringBuilder();
        sb.append("Eres un asistente nutricional. Rankea los platos del menú según el perfil del usuario y proporciona datos nutricionales.\n\n");

        sb.append("Perfil del usuario:\n");
        sb.append("- Objetivo: ").append(profile.goal() != null ? profile.goal() : "GENERAL").append("\n");
        sb.append("- Restricciones dietéticas: ").append(profile.restrictions() != null ? profile.restrictions() : List.of()).append("\n");
        sb.append("- Calorías disponibles hoy: ").append(profile.remainingCalories() != null ? profile.remainingCalories() : "sin datos").append("\n\n");

        sb.append("Platos del menú:\n");
        for (int i = 0; i < dishes.size(); i++) {
            MenuDishCandidate d = dishes.get(i);
            sb.append((i + 1)).append(". \"").append(d.name()).append("\"");
            if (d.price() != null) sb.append(" - ").append(d.price());
            sb.append("\n");
        }

        if (!existingMatches.isEmpty()) {
            sb.append("\nAlimentos existentes en la base de datos (usa matchedNameKey si alguno coincide con un plato):\n");
            for (FoodItemCandidate c : existingMatches) {
                sb.append("- nameKey: \"").append(c.nameKey())
                  .append("\", name: \"").append(c.name()).append("\"\n");
            }
        }

        sb.append("""

Para cada plato responde con un JSON array:
[{
  "dishName": "nombre exacto del plato",
  "price": "precio o null",
  "matchedNameKey": "nameKey del alimento existente si coincide, o null",
  "generatedFoodData": {
    "nameEn": "nombre en inglés",
    "nameEs": "nombre en español",
    "category": "Protein|Grain|Dairy|Vegetable|Fruit|Fat|Beverage|Other",
    "itemType": "DISH",
    "restrictions": ["LACTOSE_FREE","GLUTEN_FREE","VEGAN","VEGETARIAN","NUT_FREE","SEAFOOD_FREE","KOSHER","HALAL"],
    "weatherTypes": ["HOT","COLD","MILD"],
    "originCity": "ciudad o null",
    "originCountry": "país",
    "caloriesPer100g": number,
    "proteinPer100g": number,
    "carbsPer100g": number,
    "fatPer100g": number,
    "fiberPer100g": number,
    "sugarPer100g": number,
    "servingSize": number,
    "servingUnit": "g"
  } | null,
  "compatibilityScore": 0-100,
  "reason": "explicación breve en español de por qué es compatible/incompatible con el perfil",
  "reasonEn": "brief explanation in English of why it is compatible/incompatible with the profile",
  "estimatedCalories": number,
  "estimatedProtein": number,
  "estimatedCarbs": number,
  "estimatedFat": number
}]

Si matchedNameKey no es null, generatedFoodData debe ser null y viceversa. Responde SOLO el JSON array.""");

        return sb.toString();
    }

    private List<RankedDishData> buildFallback(List<MenuDishCandidate> dishes) {
        List<RankedDishData> result = new ArrayList<>();
        for (MenuDishCandidate d : dishes) {
            result.add(new RankedDishData(d.name(), null, d.price(), null, null,
                    50.0, "Sin datos de compatibilidad", "No compatibility data available",
                    300.0, 15.0, 30.0, 10.0));
        }
        return result;
    }

    private String stripMarkdown(String content) {
        if (content == null) return "[]";
        String trimmed = content.strip();
        if (trimmed.startsWith("```")) {
            int firstNewline = trimmed.indexOf('\n');
            int lastBacktick = trimmed.lastIndexOf("```");
            if (firstNewline != -1 && lastBacktick > firstNewline) {
                return trimmed.substring(firstNewline + 1, lastBacktick).strip();
            }
        }
        return trimmed;
    }

    private String textOrDefault(JsonNode node, String field, String defaultValue) {
        JsonNode child = node.get(field);
        return (child != null && !child.isNull()) ? child.asText() : defaultValue;
    }

    private String textOrNull(JsonNode node, String field) {
        JsonNode child = node.get(field);
        return (child != null && !child.isNull()) ? child.asText() : null;
    }

    private Double doubleOrDefault(JsonNode node, String field, Double defaultValue) {
        JsonNode child = node.get(field);
        return (child != null && !child.isNull() && child.isNumber()) ? child.asDouble() : defaultValue;
    }

    private List<String> toStringList(JsonNode arrayNode) {
        if (arrayNode == null || !arrayNode.isArray()) return List.of();
        List<String> result = new ArrayList<>();
        arrayNode.forEach(n -> result.add(n.asText()));
        return result;
    }
}
