package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.external.deepseek;

import com.devteam.nutrismart.platform.smartrecommendation.application.ports.FoodItemData;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.GeneratedCardData;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.RecommendationCardGenerationPort;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;
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
public class DeepSeekCardGenerationAdapter implements RecommendationCardGenerationPort {

    private static final Logger log = LoggerFactory.getLogger(DeepSeekCardGenerationAdapter.class);

    private final DeepSeekCardClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DeepSeekCardGenerationAdapter(DeepSeekCardClient client) {
        this.client = client;
    }

    @Override
    public List<GeneratedCardData> generateCards(List<FoodItemData> foods, String cardType,
                                                  WeatherType weatherType, String travelCity) {
        if (foods == null || foods.isEmpty()) return Collections.emptyList();

        String raw;
        try {
            raw = client.generateCardsBatch(foods, cardType, weatherType, travelCity);

        } catch (Exception e) {
            log.error("[DEEPSEEK CARD] Client error for cardType={}: {}", cardType, e.getMessage(), e);
            throw new RuntimeException("DeepSeek unavailable: " + e.getMessage(), e);
        }

        log.info("[DEEPSEEK CARD RAW] cardType={} response: {}", cardType, raw);

        String json = stripMarkdown(raw);
        try {
            List<JsonNode> nodes = objectMapper.readValue(json, new TypeReference<>() {});
            List<GeneratedCardData> result = new ArrayList<>();
            for (JsonNode node : nodes) {
                result.add(new GeneratedCardData(
                        longOrNull(node, "foodId"),
                        textOrNull(node, "description"),
                        textOrNull(node, "descriptionEs"),
                        textOrNull(node, "badge")
                ));
            }
            return result;
        } catch (Exception e) {
            log.error("[DEEPSEEK CARD] Failed to parse JSON for cardType={}: {}", cardType, e.getMessage(), e);
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

    private Long longOrNull(JsonNode node, String field) {
        JsonNode child = node.get(field);
        return (child != null && !child.isNull() && child.isNumber()) ? child.asLong() : null;
    }
}
