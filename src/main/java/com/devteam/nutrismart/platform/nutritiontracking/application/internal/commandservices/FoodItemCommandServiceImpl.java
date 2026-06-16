package com.devteam.nutrismart.platform.nutritiontracking.application.internal.commandservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.FoodItemCommandFailure;
import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.FoodItemCommandService;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.DeleteFoodItemCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.RegisterFoodItemCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.UpdateFoodItemCommand;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.FoodItemRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class FoodItemCommandServiceImpl implements FoodItemCommandService {

    private final FoodItemRepository foodItemRepository;

    public FoodItemCommandServiceImpl(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public Result<FoodItem, FoodItemCommandFailure> handle(RegisterFoodItemCommand command) {
        try {
            FoodItem item = FoodItem.create(
                    command.name(), command.nameEs(), command.source(), command.servingSize(), command.servingUnit(),
                    command.caloriesPer100g(), command.proteinPer100g(), command.carbsPer100g(),
                    command.fatPer100g(), command.fiberPer100g(), command.sugarPer100g(),
                    command.restrictions(), command.nameKey(), command.category(),
                    command.itemType(), command.weatherTypes(), command.originCity(), command.originCountry());
            return Result.success(foodItemRepository.save(item));
        } catch (Exception e) {
            return Result.failure(new FoodItemCommandFailure.InvalidData(e.getMessage()));
        }
    }

    @Override
    public Result<FoodItem, FoodItemCommandFailure> handle(UpdateFoodItemCommand command) {
        return foodItemRepository.findById(command.id())
                .map(item -> {
                    item.update(command.name(), command.nameEs(), command.source(), command.servingSize(),
                            command.servingUnit(), command.caloriesPer100g(), command.proteinPer100g(),
                            command.carbsPer100g(), command.fatPer100g(), command.fiberPer100g(),
                            command.sugarPer100g(), command.restrictions(), command.nameKey(),
                            command.category(), command.itemType(), command.weatherTypes(),
                            command.originCity(), command.originCountry());
                    return Result.<FoodItem, FoodItemCommandFailure>success(foodItemRepository.save(item));
                })
                .orElseGet(() -> Result.failure(new FoodItemCommandFailure.NotFound(command.id())));
    }

    @Override
    public Result<Void, FoodItemCommandFailure> handle(DeleteFoodItemCommand command) {
        if (!foodItemRepository.existsById(command.id())) {
            return Result.failure(new FoodItemCommandFailure.NotFound(command.id()));
        }
        foodItemRepository.deleteById(command.id());
        return Result.success(null);
    }
}
