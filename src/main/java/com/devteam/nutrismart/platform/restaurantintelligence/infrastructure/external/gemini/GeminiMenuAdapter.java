package com.devteam.nutrismart.platform.restaurantintelligence.infrastructure.external.gemini;

import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.MenuImageRecognitionPort;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.MenuDishCandidate;
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
public class GeminiMenuAdapter implements MenuImageRecognitionPort {

    private static final Logger log = LoggerFactory.getLogger(GeminiMenuAdapter.class);

    private static final String MENU_PROMPT =
            "Extract each dish and its price (if visible) from this restaurant menu. " +
            "Use English names for each dish. " +
            "Respond ONLY with JSON: [{\"name\": \"english name\", \"price\": \"...\"|null}]";

    private final GeminiMenuClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiMenuAdapter(GeminiMenuClient client) {
        this.client = client;
    }

    @Override
    public List<MenuDishCandidate> extractMenuItems(String imageBase64) {
        String raw;
        try {
            raw = client.generateContent(MENU_PROMPT, imageBase64);
        } catch (Exception e) {
            log.error("[GEMINI MENU] extractMenuItems failed: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
        log.info("[GEMINI MENU RAW] {}", raw);
        String json = stripMarkdown(raw);

        try {
            List<JsonNode> nodes = objectMapper.readValue(json, new TypeReference<>() {});
            List<MenuDishCandidate> result = new ArrayList<>();
            for (JsonNode node : nodes) {
                String name  = textOrDefault(node, "name", "Unknown dish");
                String price = textOrNull(node, "price");
                result.add(new MenuDishCandidate(name, price));
            }
            return result;
        } catch (Exception e) {
            log.error("[GEMINI MENU] Failed to parse menu response: {}", e.getMessage());
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

    private String textOrDefault(JsonNode node, String field, String defaultValue) {
        JsonNode child = node.get(field);
        return (child != null && !child.isNull()) ? child.asText() : defaultValue;
    }

    private String textOrNull(JsonNode node, String field) {
        JsonNode child = node.get(field);
        return (child != null && !child.isNull()) ? child.asText() : null;
    }
}
