package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.external.deepseek;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeepSeekCardResponse(List<Choice> choices) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Choice(Message message) {}

    public record Message(String role, String content) {}
}
