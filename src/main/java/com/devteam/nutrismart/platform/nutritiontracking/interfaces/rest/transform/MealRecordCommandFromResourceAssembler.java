package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform;

import com.devteam.nutrismart.platform.nutritiontracking.application.commands.LogMealCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.UpdateMealEntryCommand;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.CreateMealRecordResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.UpdateMealRecordResource;

public class MealRecordCommandFromResourceAssembler {

    private MealRecordCommandFromResourceAssembler() {}

    public static LogMealCommand toLogMealCommand(CreateMealRecordResource resource) {
        return new LogMealCommand(
                resource.userId(), resource.foodId(), resource.foodItemName(), resource.foodItemNameEs(),
                resource.mealType(), resource.quantity(), resource.unit(),
                resource.calories(), resource.protein(), resource.carbs(),
                resource.fat(), resource.fiber(), resource.sugar());
    }

    public static UpdateMealEntryCommand toUpdateMealEntryCommand(Long id, UpdateMealRecordResource resource) {
        return new UpdateMealEntryCommand(
                id, resource.foodItemName(), resource.foodItemNameEs(), resource.mealType(),
                resource.quantity(), resource.unit(), resource.calories(), resource.protein(),
                resource.carbs(), resource.fat(), resource.fiber(), resource.sugar());
    }
}
