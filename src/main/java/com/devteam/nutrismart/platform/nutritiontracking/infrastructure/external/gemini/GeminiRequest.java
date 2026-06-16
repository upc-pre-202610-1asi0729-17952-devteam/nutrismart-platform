package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.gemini;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Representa el cuerpo de la solicitud enviada a la API de Gemini Vision.
 * Encapsula la lista de contenidos multimodales (texto e imagen) que componen
 * el prompt enviado al modelo generativo.
 */
public record GeminiRequest(List<Content> contents) {

    /**
     * Agrupa las partes que forman un turno de conversación en el modelo Gemini.
     *
     * @param parts lista de partes del contenido (texto e imagen)
     */
    public record Content(List<Part> parts) {}

    /**
     * Representa una parte individual del contenido enviado a Gemini.
     * Puede ser un fragmento de texto o datos de imagen en Base64.
     *
     * @param text       texto del prompt; {@code null} si la parte es una imagen
     * @param inlineData datos de imagen en línea; {@code null} si la parte es texto
     */
    public record Part(String text, @JsonProperty("inline_data") InlineData inlineData) {
        /**
         * Crea una parte de texto para incluir en el prompt.
         *
         * @param text contenido textual del prompt
         * @return instancia de {@link Part} con texto y sin imagen
         */
        public static Part textPart(String text)   { return new Part(text, null); }

        /**
         * Crea una parte de imagen codificada en Base64 con tipo MIME {@code image/jpeg}.
         *
         * @param base64 imagen codificada en Base64
         * @return instancia de {@link Part} con imagen y sin texto
         */
        public static Part imagePart(String base64) {
            return new Part(null, new InlineData("image/jpeg", base64));
        }
    }

    /**
     * Contiene los datos de imagen en línea enviados a Gemini.
     *
     * @param mimeType tipo MIME de la imagen (p. ej. {@code image/jpeg})
     * @param data     imagen codificada en Base64
     */
    public record InlineData(
            @JsonProperty("mime_type") String mimeType,
            String data
    ) {}
}
