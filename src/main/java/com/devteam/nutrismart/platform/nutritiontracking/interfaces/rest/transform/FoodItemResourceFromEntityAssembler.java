package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.FoodItemResource;

public class FoodItemResourceFromEntityAssembler {

    private FoodItemResourceFromEntityAssembler() {}

    public static FoodItemResource toResource(FoodItem domain) {
        return new FoodItemResource(
                domain.getId(), domain.getName(), domain.getNameEs(), domain.getSource(),
                domain.getServingSize(), domain.getServingUnit(), domain.getCaloriesPer100g(),
                domain.getProteinPer100g(), domain.getCarbsPer100g(), domain.getFatPer100g(),
                domain.getFiberPer100g(), domain.getSugarPer100g(), domain.getRestrictions(),
                domain.getNameKey(), domain.getCategory(), domain.getItemType(),
                domain.getWeatherTypes(), domain.getOriginCity(), domain.getOriginCountry());
    }
}
