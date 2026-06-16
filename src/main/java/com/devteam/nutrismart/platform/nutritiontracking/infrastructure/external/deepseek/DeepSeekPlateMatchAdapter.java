package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.deepseek;

import com.devteam.nutrismart.platform.nutritiontracking.application.ports.FoodItemCandidate;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.PlateItemMatchPort;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.PlateItemMatchResult;
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
public class DeepSeekPlateMatchAdapter implements PlateItemMatchPort {

    private static final Logger log = LoggerFactory.getLogger(DeepSeekPlateMatchAdapter.class);

    private final DeepSeekClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DeepSeekPlateMatchAdapter(DeepSeekClient client) {
        this.client = client;
    }

    @Override
    public List<PlateItemMatchResult> matchOrEstimate(
            List<String> detectedNames,
            Map<String, List<FoodItemCandidate>> candidatesByName) {

        if (detectedNames == null || detectedNames.isEmpty()) return Collections.emptyList();

        String prompt = buildPrompt(detectedNames, candidatesByName);
        String raw;
        try {
            raw = client.callDeepSeek(prompt);
        } catch (Exception e) {
            log.error("[DEEPSEEK PLATE] callDeepSeek failed: {}", e.getMessage(), e);
            return buildFallback(detectedNames);
        }
        log.info("[DEEPSEEK PLATE RAW] {}", raw);
        String json = stripMarkdown(raw);

        try {
            List<JsonNode> nodes = objectMapper.readValue(json, new TypeReference<>() {});
            List<PlateItemMatchResult> results = new ArrayList<>();
            for (int i = 0; i < nodes.size(); i++) {
                JsonNode node = nodes.get(i);
                String detectedName = i < detectedNames.size() ? detectedNames.get(i) : "Unknown";
                results.add(new PlateItemMatchResult(
                        detectedName,
                        textOrNull(node, "matchedNameKey"),
                        textOrDefault(node, "name", detectedName),
                        textOrNull(node, "nameEs"),
                        doubleOrDefault(node, "caloriesPer100g", 100.0),
                        doubleOrDefault(node, "proteinPer100g", 5.0),
                        doubleOrDefault(node, "carbsPer100g", 15.0),
                        doubleOrDefault(node, "fatPer100g", 3.0)
                ));
            }
            if (results.size() < detectedNames.size()) {
                for (int i = results.size(); i < detectedNames.size(); i++) {
                    results.add(fallbackResult(detectedNames.get(i)));
                }
            }
            return results;
        } catch (Exception e) {
            log.error("[DEEPSEEK PLATE] Failed to parse response: {}", e.getMessage());
            return buildFallback(detectedNames);
        }
    }

    private String buildPrompt(List<String> detectedNames, Map<String, List<FoodItemCandidate>> candidatesByName) {
        StringBuilder sb = new StringBuilder();
        sb.append("For each detected food item, find the best match among the existing candidates ");
        sb.append("or estimate macros per 100g if there is no adequate match.\n\n");
        sb.append("Detected foods with candidates:\n");

        for (String name : detectedNames) {
            sb.append("- \"").append(name).append("\"");
            List<FoodItemCandidate> candidates = candidatesByName.getOrDefault(name, List.of());
            if (!candidates.isEmpty()) {
                sb.append(" | Candidates: ");
                for (FoodItemCandidate c : candidates) {
                    sb.append("{nameKey: \"").append(c.nameKey()).append("\", name: \"").append(c.name()).append("\"} ");
                }
            }
            sb.append("\n");
        }

        sb.append("""

Respond ONLY with a JSON array with one object per food in the same order:
[{
  "detectedName": "...",
  "matchedNameKey": "..." | null,
  "name": "food name in English (required, even if matched)",
  "nameEs": "food name in Spanish (required, even if matched)",
  "caloriesPer100g": number,
  "proteinPer100g": number,
  "carbsPer100g": number,
  "fatPer100g": number
}]

If there is a good match, set matchedNameKey and use the match macros. If there is no match, set matchedNameKey: null and estimate the macros.""");

        return sb.toString();
    }

    private List<PlateItemMatchResult> buildFallback(List<String> detectedNames) {
        return detectedNames.stream().map(this::fallbackResult).toList();
    }

    private PlateItemMatchResult fallbackResult(String name) {
        return new PlateItemMatchResult(name, null, name, name, 100.0, 5.0, 15.0, 3.0);
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
}
