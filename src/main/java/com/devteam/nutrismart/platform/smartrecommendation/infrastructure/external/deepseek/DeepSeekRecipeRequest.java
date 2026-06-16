package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.external.deepseek;

import java.util.List;

public record DeepSeekRecipeRequest(
        String model,
        List<Message> messages,
        double temperature
) {
    public record Message(String role, String content) {}
}
