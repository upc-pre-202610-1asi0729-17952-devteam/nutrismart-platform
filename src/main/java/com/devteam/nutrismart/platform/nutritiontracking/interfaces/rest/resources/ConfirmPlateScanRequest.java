package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources;

import java.util.List;

public record ConfirmPlateScanRequest(
        String mealType,
        List<ConfirmedItemRequest> items
) {
    public record ConfirmedItemRequest(
            Long    foodItemId,
            String  name,
            String  nameEs,
            int     quantityG,
            double  caloriesPer100g,
            double  proteinPer100g,
            double  carbsPer100g,
            double  fatPer100g,
            boolean isEstimate
    ) {}
}
