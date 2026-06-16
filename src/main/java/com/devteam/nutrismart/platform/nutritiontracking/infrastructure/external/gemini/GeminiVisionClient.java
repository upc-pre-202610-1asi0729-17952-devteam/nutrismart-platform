package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.gemini;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Cliente HTTP para la API de Gemini Vision de Google.
 * <p>
 * Construye y envía solicitudes a la API de generación de contenido de Gemini,
 * combinando un prompt de texto con una imagen codificada en Base64,
 * y retorna el texto generado por el modelo.
 * </p>
 */
@Component
public class GeminiVisionClient {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;
    private final String model;

    public GeminiVisionClient(
            @Qualifier("geminiRestTemplate") RestTemplate restTemplate,
            @Value("${gemini.api-key}") String apiKey,
            @Value("${gemini.base-url}") String baseUrl,
            @Value("${gemini.model}") String model) {
        this.restTemplate = restTemplate;
        this.apiKey       = apiKey;
        this.baseUrl      = baseUrl;
        this.model        = model;
    }

    /**
     * Envía un prompt de texto junto con una imagen a la API de Gemini Vision
     * y retorna el texto generado por el modelo.
     * <p>
     * El prefijo data-URL de la imagen (p. ej. {@code data:image/jpeg;base64,}) se elimina
     * automáticamente antes de enviar la solicitud.
     * </p>
     *
     * @param prompt      instrucción textual que describe la tarea al modelo
     * @param imageBase64 imagen codificada en Base64 (con o sin prefijo data-URL)
     * @return texto generado por el modelo; {@code "[]"} si la respuesta es nula o vacía
     */
    public String generateContent(String prompt, String imageBase64) {
        String cleanBase64 = stripDataUrlPrefix(imageBase64);

        var request = new GeminiRequest(List.of(
                new GeminiRequest.Content(List.of(
                        GeminiRequest.Part.textPart(prompt),
                        GeminiRequest.Part.imagePart(cleanBase64)
                ))
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = baseUrl + "/v1beta/models/" + model + ":generateContent?key=" + apiKey;
        HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);

        GeminiResponse response = restTemplate.postForObject(url, entity, GeminiResponse.class);

        if (response == null || response.candidates() == null || response.candidates().isEmpty()) return "[]";
        var parts = response.candidates().get(0).content().parts();
        if (parts == null || parts.isEmpty()) return "[]";
        return parts.get(0).text();
    }

    private String stripDataUrlPrefix(String base64) {
        if (base64 == null) return "";
        int commaIdx = base64.indexOf(',');
        return commaIdx >= 0 ? base64.substring(commaIdx + 1) : base64;
    }
}
