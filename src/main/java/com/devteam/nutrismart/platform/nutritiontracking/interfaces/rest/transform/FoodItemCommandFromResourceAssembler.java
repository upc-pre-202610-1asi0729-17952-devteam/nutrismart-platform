package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform;

import com.devteam.nutrismart.platform.nutritiontracking.application.commands.RegisterFoodItemCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.UpdateFoodItemCommand;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.CreateFoodItemResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.UpdateFoodItemResource;

public class FoodItemCommandFromResourceAssembler {

    private FoodItemCommandFromResourceAssembler() {}

    public static RegisterFoodItemCommand toRegisterFoodItemCommand(CreateFoodItemResource resource) {
        return new RegisterFoodItemCommand(
                resource.name(), resource.nameEs(), resource.source(), resource.servingSize(),
                resource.servingUnit(), resource.caloriesPer100g(), resource.proteinPer100g(),
                resource.carbsPer100g(), resource.fatPer100g(), resource.fiberPer100g(),
                resource.sugarPer100g(), resource.restrictions(), resource.nameKey(),
                resource.category(), resource.itemType(), resource.weatherTypes(),
                resource.originCity(), resource.originCountry());
    }

    public static UpdateFoodItemCommand toUpdateFoodItemCommand(Long id, UpdateFoodItemResource resource) {
        return new UpdateFoodItemCommand(
                id, resource.name(), resource.nameEs(), resource.source(), resource.servingSize(),
                resource.servingUnit(), resource.caloriesPer100g(), resource.proteinPer100g(),
                resource.carbsPer100g(), resource.fatPer100g(), resource.fiberPer100g(),
                resource.sugarPer100g(), resource.restrictions(), resource.nameKey(),
                resource.category(), resource.itemType(), resource.weatherTypes(),
                resource.originCity(), resource.originCountry());
    }
}
