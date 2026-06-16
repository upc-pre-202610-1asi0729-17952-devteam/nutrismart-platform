package com.devteam.nutrismart.platform.nutritiontracking.application.internal.commandservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.MealRecordCommandFailure;
import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.MealRecordCommandService;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.DeleteMealLogCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.LogMealCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.UpdateMealEntryCommand;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.MealRecord;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.MealRecordRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class MealRecordCommandServiceImpl implements MealRecordCommandService {

    private final MealRecordRepository mealRecordRepository;

    public MealRecordCommandServiceImpl(MealRecordRepository mealRecordRepository) {
        this.mealRecordRepository = mealRecordRepository;
    }

    @Override
    public Result<MealRecord, MealRecordCommandFailure> handle(LogMealCommand command) {
        try {
            MealRecord record = MealRecord.create(
                    command.userId(), command.foodId(), command.foodItemName(), command.foodItemNameEs(),
                    command.mealType(), command.quantity(), command.unit(),
                    command.calories(), command.protein(), command.carbs(),
                    command.fat(), command.fiber(), command.sugar());
            return Result.success(mealRecordRepository.save(record));
        } catch (Exception e) {
            return Result.failure(new MealRecordCommandFailure.InvalidData(e.getMessage()));
        }
    }

    @Override
    public Result<MealRecord, MealRecordCommandFailure> handle(UpdateMealEntryCommand command) {
        return mealRecordRepository.findById(command.id())
                .map(record -> {
                    record.update(command.foodItemName(), command.foodItemNameEs(), command.mealType(),
                            command.quantity(), command.unit(), command.calories(), command.protein(),
                            command.carbs(), command.fat(), command.fiber(), command.sugar());
                    return Result.<MealRecord, MealRecordCommandFailure>success(mealRecordRepository.save(record));
                })
                .orElseGet(() -> Result.failure(new MealRecordCommandFailure.NotFound(command.id())));
    }

    @Override
    public Result<Void, MealRecordCommandFailure> handle(DeleteMealLogCommand command) {
        if (!mealRecordRepository.existsById(command.id())) {
            return Result.failure(new MealRecordCommandFailure.NotFound(command.id()));
        }
        mealRecordRepository.deleteById(command.id());
        return Result.success(null);
    }
}
