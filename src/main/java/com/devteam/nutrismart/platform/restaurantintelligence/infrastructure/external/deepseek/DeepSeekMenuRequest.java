package com.devteam.nutrismart.platform.restaurantintelligence.infrastructure.external.deepseek;

import java.util.List;

public record DeepSeekMenuRequest(
        String model,
        List<Message> messages,
        double temperature
) {
    public record Message(String role, String content) {}
}
