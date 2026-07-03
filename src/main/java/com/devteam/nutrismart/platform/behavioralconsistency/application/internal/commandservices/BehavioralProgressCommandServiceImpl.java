package com.devteam.nutrismart.platform.behavioralconsistency.application.internal.commandservices;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.CreateBehavioralProgressCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.RegisterSuccessfulDayCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.UpdateBehavioralProgressCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices.BehavioralProgressCommandFailure;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices.BehavioralProgressCommandService;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.BehavioralProgress;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.events.SuccessfulDayRegisteredEvent;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.repositories.BehavioralProgressRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
/**
 * Implementación del servicio de comandos para el progreso conductual.
 * Orquesta la creación y actualización del agregado {@code BehavioralProgress}
 * delegando la persistencia al repositorio de dominio correspondiente.
 */
public class BehavioralProgressCommandServiceImpl implements BehavioralProgressCommandService {

    private final BehavioralProgressRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public BehavioralProgressCommandServiceImpl(BehavioralProgressRepository repository,
                                                ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<BehavioralProgress, BehavioralProgressCommandFailure> handle(CreateBehavioralProgressCommand command) {
        try {
            var progress = BehavioralProgress.create(command.userId());
            var saved = repository.save(progress);
            return Result.success(saved);
        } catch (Exception ex) {
            return Result.failure(new BehavioralProgressCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<BehavioralProgress, BehavioralProgressCommandFailure> handle(UpdateBehavioralProgressCommand command) {
        try {
            return repository.findById(command.id())
                    .map(progress -> {
                        progress.update(
                                command.adherenceStatus(),
                                command.streak(),
                                command.consecutiveMisses(),
                                command.weeklyCompletionRate(),
                                command.lastEvaluatedAt());
                        if (command.goalMetDates() != null) {
                            command.goalMetDates().forEach(progress::markGoalMet);
                        }
                        if (command.goalMetToday()) {
                            progress.markGoalMet(LocalDate.now());
                        }
                        var saved = repository.save(progress);
                        return Result.<BehavioralProgress, BehavioralProgressCommandFailure>success(saved);
                    })
                    .orElse(Result.failure(new BehavioralProgressCommandFailure.BehavioralProgressNotFound(command.id())));
        } catch (Exception ex) {
            return Result.failure(new BehavioralProgressCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<BehavioralProgress, BehavioralProgressCommandFailure> handle(RegisterSuccessfulDayCommand command) {
        try {
            return repository.findByUserId(command.userId())
                    .map(progress -> {
                        progress.registerSuccessfulDay(command.date());
                        var saved = repository.save(progress);
                        eventPublisher.publishEvent(SuccessfulDayRegisteredEvent.of(
                                saved.getUserId(), command.date(), saved.getStreak()));
                        return Result.<BehavioralProgress, BehavioralProgressCommandFailure>success(saved);
                    })
                    .orElse(Result.failure(new BehavioralProgressCommandFailure.BehavioralProgressNotFound(command.userId())));
        } catch (Exception ex) {
            return Result.failure(new BehavioralProgressCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
