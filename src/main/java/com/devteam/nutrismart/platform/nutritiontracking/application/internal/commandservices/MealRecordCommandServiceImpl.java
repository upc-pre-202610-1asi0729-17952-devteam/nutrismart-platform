package com.devteam.nutrismart.platform.nutritiontracking.application.internal.commandservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.MealRecordCommandFailure;
import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.MealRecordCommandService;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.DeleteMealLogCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.LogMealCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.UpdateMealEntryCommand;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.MealRecord;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.events.MealEntryRemovedEvent;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.events.MealEntryUpdatedEvent;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.events.MealLoggedEvent;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.MealRecordRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@Service
public class MealRecordCommandServiceImpl implements MealRecordCommandService {

    private final MealRecordRepository mealRecordRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MealRecordCommandServiceImpl(MealRecordRepository mealRecordRepository,
                                        ApplicationEventPublisher eventPublisher) {
        this.mealRecordRepository = mealRecordRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<MealRecord, MealRecordCommandFailure> handle(LogMealCommand command) {
        try {
            MealRecord record = MealRecord.create(
                    command.userId(), command.foodId(), command.foodItemName(), command.foodItemNameEs(),
                    command.mealType(), command.quantity(), command.unit(),
                    command.calories(), command.protein(), command.carbs(),
                    command.fat(), command.fiber(), command.sugar());
            MealRecord saved = mealRecordRepository.save(record);
            eventPublisher.publishEvent(MealLoggedEvent.of(
                    saved.getUserId(), saved.getLoggedAt().atZone(ZoneId.systemDefault()).toLocalDate()));
            return Result.success(saved);
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
                    MealRecord saved = mealRecordRepository.save(record);
                    eventPublisher.publishEvent(MealEntryUpdatedEvent.of(
                            saved.getUserId(), saved.getLoggedAt().atZone(ZoneId.systemDefault()).toLocalDate()));
                    return Result.<MealRecord, MealRecordCommandFailure>success(saved);
                })
                .orElseGet(() -> Result.failure(new MealRecordCommandFailure.NotFound(command.id())));
    }

    @Override
    public Result<Void, MealRecordCommandFailure> handle(DeleteMealLogCommand command) {
        return mealRecordRepository.findById(command.id())
                .map(record -> {
                    mealRecordRepository.deleteById(record.getId());
                    eventPublisher.publishEvent(MealEntryRemovedEvent.of(
                            record.getUserId(), record.getLoggedAt().atZone(ZoneId.systemDefault()).toLocalDate()));
                    return Result.<Void, MealRecordCommandFailure>success(null);
                })
                .orElseGet(() -> Result.failure(new MealRecordCommandFailure.NotFound(command.id())));
    }
}
