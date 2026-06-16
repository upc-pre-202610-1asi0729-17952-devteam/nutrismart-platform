package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.external.deepseek;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.GeneratedRecipeData;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.RecipeGenerationPort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class DeepSeekRecipeGenerationAdapter implements RecipeGenerationPort {

    private static final Logger log = LoggerFactory.getLogger(DeepSeekRecipeGenerationAdapter.class);

    private final DeepSeekRecipeClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DeepSeekRecipeGenerationAdapter(DeepSeekRecipeClient client) {
        this.client = client;
    }

    @Override
    public List<GeneratedRecipeData> generateRecipes(Map<String, List<String>> ingredientsByCategory,
                                                      UserGoal goalType,
                                                      int count) {
        if (ingredientsByCategory == null || ingredientsByCategory.isEmpty()) return Collections.emptyList();

        String raw;
        try {
            raw = client.generateRecipesBatch(ingredientsByCategory, goalType, count);
        } catch (Exception e) {
            log.error("[DEEPSEEK RECIPE] Client error for goal={}: {}", goalType, e.getMessage(), e);
            throw new RuntimeException("DeepSeek unavailable: " + e.getMessage(), e);
        }

        log.info("[DEEPSEEK RECIPE RAW] goal={} response: {}", goalType, raw);

        String json = stripMarkdown(raw);
        try {
            List<JsonNode> nodes = objectMapper.readValue(json, new TypeReference<>() {});
            List<GeneratedRecipeData> result = new ArrayList<>();
            for (JsonNode node : nodes) {
                result.add(new GeneratedRecipeData(
                        textOrNull(node, "name"),
                        textOrNull(node, "nameEs"),
                        intOrNull(node, "prepTimeMinutes"),
                        doubleOrNull(node, "estimatedCalories"),
                        doubleOrNull(node, "estimatedProtein"),
                        doubleOrNull(node, "estimatedCarbs"),
                        doubleOrNull(node, "estimatedFat"),
                        toStringList(node.get("ingredients"))
                ));
            }
            return result;
        } catch (Exception e) {
            log.error("[DEEPSEEK RECIPE] Failed to parse JSON for goal={}: {}", goalType, e.getMessage(), e);
            return Collections.emptyList();
        }
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

    private String textOrNull(JsonNode node, String field) {
        JsonNode child = node.get(field);
        return (child != null && !child.isNull()) ? child.asText() : null;
    }

    private Integer intOrNull(JsonNode node, String field) {
        JsonNode child = node.get(field);
        return (child != null && !child.isNull() && child.isNumber()) ? child.asInt() : null;
    }

    private Double doubleOrNull(JsonNode node, String field) {
        JsonNode child = node.get(field);
        return (child != null && !child.isNull() && child.isNumber()) ? child.asDouble() : null;
    }

    private List<String> toStringList(JsonNode arrayNode) {
        if (arrayNode == null || !arrayNode.isArray()) return List.of();
        List<String> result = new ArrayList<>();
        arrayNode.forEach(n -> result.add(n.asText()));
        return result;
    }
}
