package com.devteam.nutrismart.platform.behavioralconsistency.application.internal.commandservices;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.CreateRecoveryPlanCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.DeleteRecoveryPlanCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.UpdateRecoveryPlanCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices.RecoveryPlanCommandFailure;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices.RecoveryPlanCommandService;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.RecoveryPlan;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.repositories.RecoveryPlanRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
/**
 * Implementación del servicio de comandos para los planes de recuperación.
 * Orquesta la creación, actualización y eliminación del agregado {@code RecoveryPlan}
 * delegando la persistencia al repositorio de dominio correspondiente.
 */
public class RecoveryPlanCommandServiceImpl implements RecoveryPlanCommandService {

    private final RecoveryPlanRepository repository;

    public RecoveryPlanCommandServiceImpl(RecoveryPlanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<RecoveryPlan, RecoveryPlanCommandFailure> handle(CreateRecoveryPlanCommand command) {
        try {
            var plan = RecoveryPlan.create(command.userId(), command.trigger(), command.actions(), command.expiresAt());
            var saved = repository.save(plan);
            return Result.success(saved);
        } catch (Exception ex) {
            return Result.failure(new RecoveryPlanCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<RecoveryPlan, RecoveryPlanCommandFailure> handle(UpdateRecoveryPlanCommand command) {
        try {
            return repository.findById(command.id())
                    .map(plan -> {
                        plan.update(command.status());
                        var saved = repository.save(plan);
                        return Result.<RecoveryPlan, RecoveryPlanCommandFailure>success(saved);
                    })
                    .orElse(Result.failure(new RecoveryPlanCommandFailure.RecoveryPlanNotFound(command.id())));
        } catch (Exception ex) {
            return Result.failure(new RecoveryPlanCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<Void, RecoveryPlanCommandFailure> handle(DeleteRecoveryPlanCommand command) {
        if (repository.findById(command.id()).isEmpty()) {
            return Result.failure(new RecoveryPlanCommandFailure.RecoveryPlanNotFound(command.id()));
        }
        repository.deleteById(command.id());
        return Result.success(null);
    }
}
