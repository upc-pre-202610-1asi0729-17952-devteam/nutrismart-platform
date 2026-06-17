package com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto;

import java.util.List;

public record FoodItemCandidate(Long id, String nameKey, String name, String nameEs, List<String> restrictions) {}
