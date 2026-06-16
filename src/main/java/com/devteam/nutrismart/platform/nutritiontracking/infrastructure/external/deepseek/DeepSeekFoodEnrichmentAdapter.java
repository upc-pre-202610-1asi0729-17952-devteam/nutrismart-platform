package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.deepseek;

import com.devteam.nutrismart.platform.nutritiontracking.application.ports.EnrichedFoodData;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.FoodEnrichmentPort;
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
public class DeepSeekFoodEnrichmentAdapter implements FoodEnrichmentPort {

    private static final Logger log = LoggerFactory.getLogger(DeepSeekFoodEnrichmentAdapter.class);

    private final DeepSeekClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DeepSeekFoodEnrichmentAdapter(DeepSeekClient client) {
        this.client = client;
    }

    @Override
    public List<EnrichedFoodData> enrichBatch(List<String> foodNamesInEnglish) {
        if (foodNamesInEnglish == null || foodNamesInEnglish.isEmpty()) return Collections.emptyList();

        String raw;
        try {
            raw = client.enrichFoodsBatch(foodNamesInEnglish);
        } catch (Exception e) {
            log.error("[DEEPSEEK CLIENT ERROR] Fallo al llamar a DeepSeek: {}", e.getMessage(), e);
            return foodNamesInEnglish.stream()
                    .map(name -> new EnrichedFoodData(name, name, "Other", "INGREDIENT", List.of(), List.of(), null, "United States"))
                    .toList();
        }
        log.info("[DEEPSEEK RAW] {}", raw);
        String json = stripMarkdown(raw);

        try {
            List<JsonNode> nodes = objectMapper.readValue(json, new TypeReference<>() {});
            List<EnrichedFoodData> result = new ArrayList<>();
            for (int i = 0; i < nodes.size(); i++) {
                JsonNode node = nodes.get(i);
                String originalName = i < foodNamesInEnglish.size() ? foodNamesInEnglish.get(i) : "Unknown";
                result.add(new EnrichedFoodData(
                        textOrDefault(node, "nameEn", originalName),
                        textOrDefault(node, "nameEs", originalName),
                        textOrDefault(node, "category", "Other"),
                        textOrDefault(node, "itemType", "INGREDIENT"),
                        toStringList(node.get("restrictions")),
                        toStringList(node.get("weatherTypes")),
                        textOrNull(node, "originCity"),
                        textOrDefault(node, "originCountry", "United States")
                ));
            }
            return result;
        } catch (Exception e) {
            return foodNamesInEnglish.stream()
                    .map(name -> new EnrichedFoodData(name, name, "Other", "INGREDIENT", List.of(), List.of(), null, "United States"))
                    .toList();
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

    private String textOrDefault(JsonNode node, String field, String defaultValue) {
        JsonNode child = node.get(field);
        return (child != null && !child.isNull()) ? child.asText() : defaultValue;
    }

    private String textOrNull(JsonNode node, String field) {
        JsonNode child = node.get(field);
        return (child != null && !child.isNull()) ? child.asText() : null;
    }

    private List<String> toStringList(JsonNode arrayNode) {
        if (arrayNode == null || !arrayNode.isArray()) return List.of();
        List<String> result = new ArrayList<>();
        arrayNode.forEach(n -> result.add(n.asText()));
        return result;
    }
}
