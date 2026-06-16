package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.MealType;

public record UpdateMealRecordResource(
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
        Double sugar
) {}
