package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.gemini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Representa la respuesta devuelta por la API de Gemini Vision.
 * Contiene la lista de candidatos generados por el modelo, de los cuales
 * normalmente se utiliza el primero para extraer el texto generado.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GeminiResponse(List<Candidate> candidates) {

    /**
     * Candidato de respuesta generado por el modelo Gemini.
     * Cada candidato incluye el contenido producido por el modelo.
     *
     * @param content contenido textual del candidato
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Candidate(Content content) {}

    /**
     * Contenido de un candidato de respuesta; agrupa las partes textuales
     * que componen la respuesta del modelo.
     *
     * @param parts lista de partes de texto
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Content(List<Part> parts) {}

    /**
     * Fragmento de texto dentro del contenido de un candidato.
     *
     * @param text texto generado por el modelo
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Part(String text) {}
}
