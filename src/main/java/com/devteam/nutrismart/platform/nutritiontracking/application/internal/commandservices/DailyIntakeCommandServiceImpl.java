package com.devteam.nutrismart.platform.nutritiontracking.application.internal.commandservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.DailyIntakeCommandFailure;
import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.DailyIntakeCommandService;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.CreateDailyIntakeCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.UpdateDailyIntakeCommand;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.DailyIntake;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.events.DailyGoalReachedEvent;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.DailyIntakeRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class DailyIntakeCommandServiceImpl implements DailyIntakeCommandService {

    private final DailyIntakeRepository dailyIntakeRepository;
    private final ApplicationEventPublisher eventPublisher;

    public DailyIntakeCommandServiceImpl(DailyIntakeRepository dailyIntakeRepository,
                                         ApplicationEventPublisher eventPublisher) {
        this.dailyIntakeRepository = dailyIntakeRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<DailyIntake, DailyIntakeCommandFailure> handle(CreateDailyIntakeCommand command) {
        if (dailyIntakeRepository.existsByUserIdAndDate(command.userId(), command.date())) {
            return Result.failure(new DailyIntakeCommandFailure.DuplicateEntry(command.userId(), command.date()));
        }
        try {
            DailyIntake intake = DailyIntake.create(
                    command.userId(), command.date(), command.dailyGoal(), command.consumed(), command.active());
            DailyIntake saved = dailyIntakeRepository.save(intake);
            publishIfGoalReached(saved);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(new DailyIntakeCommandFailure.InvalidData(e.getMessage()));
        }
    }

    @Override
    public Result<DailyIntake, DailyIntakeCommandFailure> handle(UpdateDailyIntakeCommand command) {
        return dailyIntakeRepository.findById(command.id())
                .map(intake -> {
                    intake.update(command.dailyGoal(), command.consumed(), command.active());
                    DailyIntake saved = dailyIntakeRepository.save(intake);
                    publishIfGoalReached(saved);
                    return Result.<DailyIntake, DailyIntakeCommandFailure>success(saved);
                })
                .orElseGet(() -> Result.failure(new DailyIntakeCommandFailure.NotFound(command.id())));
    }

    /**
     * Emite {@link DailyGoalReachedEvent} cuando el registro diario alcanza la meta calórica,
     * delegando la regla de negocio en el propio agregado.
     */
    private void publishIfGoalReached(DailyIntake intake) {
        if (intake.hasReachedGoal()) {
            eventPublisher.publishEvent(DailyGoalReachedEvent.of(
                    intake.getUserId(), intake.getDate(), intake.getConsumed(), intake.getDailyGoal()));
        }
    }
}
