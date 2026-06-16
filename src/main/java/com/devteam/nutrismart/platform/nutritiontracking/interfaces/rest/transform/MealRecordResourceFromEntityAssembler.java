package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.MealRecord;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.MealRecordResource;

public class MealRecordResourceFromEntityAssembler {

    private MealRecordResourceFromEntityAssembler() {}

    public static MealRecordResource toResource(MealRecord domain) {
        return new MealRecordResource(
                domain.getId(), domain.getUserId(), domain.getFoodId(),
                domain.getFoodItemName(), domain.getFoodItemNameEs(),
                domain.getMealType(), domain.getQuantity(), domain.getUnit(),
                domain.getCalories(), domain.getProtein(), domain.getCarbs(),
                domain.getFat(), domain.getFiber(), domain.getSugar(), domain.getLoggedAt());
    }
}
