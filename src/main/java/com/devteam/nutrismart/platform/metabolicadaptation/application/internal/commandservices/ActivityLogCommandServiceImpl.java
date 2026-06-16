package com.devteam.nutrismart.platform.metabolicadaptation.application.internal.commandservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.ActivityLogCommandFailure;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.ActivityLogCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.DeleteActivityLogCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.LogActivityCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.ActivityLog;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.ActivityLogRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación transaccional del servicio de comandos para el agregado {@code ActivityLog}.
 * Delega la persistencia al repositorio de dominio y envuelve los resultados en {@code Result}.
 */
@Service
@Transactional
public class ActivityLogCommandServiceImpl implements ActivityLogCommandService {

    private final ActivityLogRepository repository;

    public ActivityLogCommandServiceImpl(ActivityLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<ActivityLog, ActivityLogCommandFailure> handle(LogActivityCommand command) {
        try {
            ActivityLog al = ActivityLog.create(command.userId(), command.activityType(),
                    command.durationMinutes(), command.caloriesBurned(), command.timestamp());
            return Result.success(repository.save(al));
        } catch (Exception ex) {
            return Result.failure(new ActivityLogCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<Void, ActivityLogCommandFailure> handle(DeleteActivityLogCommand command) {
        if (!repository.existsById(command.id())) {
            return Result.failure(new ActivityLogCommandFailure.ActivityLogNotFound(command.id()));
        }
        repository.deleteById(command.id());
        return Result.success(null);
    }
}
