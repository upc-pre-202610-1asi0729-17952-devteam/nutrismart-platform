package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.MealType;

import java.time.Instant;

public record MealRecordResource(
        Long id,
        Long userId,
        Long foodId,
        String foodItemName,
        String foodItemNameEs,
        MealType mealType,
        Double quantity,
        String unit,
        Double calories,
        Double protein,
        Double carbs,
        Double fat,
        Double fiber,
        Double sugar,
        Instant loggedAt
) {}
