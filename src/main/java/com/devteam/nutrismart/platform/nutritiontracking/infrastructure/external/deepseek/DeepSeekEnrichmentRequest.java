package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.deepseek;

import java.util.List;

public record DeepSeekEnrichmentRequest(
        String model,
        List<Message> messages,
        double temperature
) {
    public record Message(String role, String content) {}
}
