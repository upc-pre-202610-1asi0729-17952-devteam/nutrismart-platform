package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.gemini;

import com.devteam.nutrismart.platform.nutritiontracking.application.ports.DetectedFoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.ImageRecognitionPort;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.MenuDishCandidate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adaptador de infraestructura que implementa {@link ImageRecognitionPort} utilizando
 * la API de Gemini Vision de Google.
 * <p>
 * Se encarga de enviar imágenes codificadas en Base64 junto con prompts específicos
 * al modelo de visión de Gemini y de transformar la respuesta JSON en los tipos
 * del dominio ({@link DetectedFoodItem} y {@link MenuDishCandidate}).
 * </p>
 */
@Component
public class GeminiVisionAdapter implements ImageRecognitionPort {

    private static final Logger log = LoggerFactory.getLogger(GeminiVisionAdapter.class);

    private static final String PLATE_PROMPT =
            "Identify each visible food item in this plate image and estimate the quantity in grams. " +
            "Use English names for each item. " +
            "Respond ONLY with JSON: [{\"name\": \"english name\", \"estimatedQuantityG\": number}]";

    private static final String MENU_PROMPT =
            "Extract each dish and its price (if visible) from this restaurant menu. " +
            "Use English names for each dish. " +
            "Respond ONLY with JSON: [{\"name\": \"english name\", \"price\": \"...\"|null}]";

    private final GeminiVisionClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiVisionAdapter(GeminiVisionClient client) {
        this.client = client;
    }

    /**
     * Identifica los alimentos presentes en la imagen de un plato y estima
     * la cantidad en gramos de cada uno.
     * <p>
     * Envía la imagen junto con un prompt estructurado a Gemini Vision y parsea
     * la respuesta JSON para construir la lista de {@link DetectedFoodItem}.
     * En caso de error en la llamada o en el parseo, retorna una lista vacía.
     * </p>
     *
     * @param imageBase64 imagen del plato codificada en Base64 (puede incluir prefijo data-URL)
     * @return lista de alimentos detectados con su cantidad estimada en gramos;
     *         lista vacía si la operación falla
     */
    @Override
    public List<DetectedFoodItem> identifyPlateContents(String imageBase64) {
        String raw;
        try {
            raw = client.generateContent(PLATE_PROMPT, imageBase64);
        } catch (Exception e) {
            log.error("[GEMINI] identifyPlateContents failed: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
        log.info("[GEMINI PLATE RAW] {}", raw);
        String json = stripMarkdown(raw);

        try {
            List<JsonNode> nodes = objectMapper.readValue(json, new TypeReference<>() {});
            List<DetectedFoodItem> result = new ArrayList<>();
            for (JsonNode node : nodes) {
                String name = textOrDefault(node, "name", "Unknown food");
                int qty = node.has("estimatedQuantityG") ? node.get("estimatedQuantityG").asInt(100) : 100;
                result.add(new DetectedFoodItem(name, qty));
            }
            return result;
        } catch (Exception e) {
            log.error("[GEMINI] Failed to parse plate response: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Extrae los platos y sus precios a partir de la imagen de un menú de restaurante.
     * <p>
     * Envía la imagen junto con un prompt específico a Gemini Vision y parsea la
     * respuesta JSON para construir la lista de {@link MenuDishCandidate}.
     * En caso de error en la llamada o en el parseo, retorna una lista vacía.
     * </p>
     *
     * @param imageBase64 imagen del menú codificada en Base64 (puede incluir prefijo data-URL)
     * @return lista de platos detectados con su precio (si es visible);
     *         lista vacía si la operación falla
     */
    @Override
    public List<MenuDishCandidate> extractMenuItems(String imageBase64) {
        String raw;
        try {
            raw = client.generateContent(MENU_PROMPT, imageBase64);
        } catch (Exception e) {
            log.error("[GEMINI] extractMenuItems failed: {}", e.getMessage(), e);
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
            log.error("[GEMINI] Failed to parse menu response: {}", e.getMessage());
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
