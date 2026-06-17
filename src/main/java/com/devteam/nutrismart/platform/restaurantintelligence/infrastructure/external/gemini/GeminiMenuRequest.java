package com.devteam.nutrismart.platform.restaurantintelligence.infrastructure.external.gemini;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GeminiMenuRequest(List<Content> contents) {

    public record Content(List<Part> parts) {}

    public record Part(String text, @JsonProperty("inline_data") InlineData inlineData) {
        public static Part textPart(String text)    { return new Part(text, null); }
        public static Part imagePart(String base64) {
            return new Part(null, new InlineData("image/jpeg", base64));
        }
    }

    public record InlineData(
            @JsonProperty("mime_type") String mimeType,
            String data
    ) {}
}
