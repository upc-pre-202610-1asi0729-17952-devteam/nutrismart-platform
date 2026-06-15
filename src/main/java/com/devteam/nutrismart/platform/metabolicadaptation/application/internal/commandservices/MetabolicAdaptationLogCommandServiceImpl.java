package com.devteam.nutrismart.platform.metabolicadaptation.application.internal.commandservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.MetabolicAdaptationLogCommandFailure;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.MetabolicAdaptationLogCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.RecordMetabolicAdaptationCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.MetabolicAdaptationLog;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.MetabolicAdaptationLogRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación transaccional del servicio de comandos para el agregado {@code MetabolicAdaptationLog}.
 * Registra cada evento de adaptación metabólica en el repositorio de dominio.
 */
@Service
@Transactional
public class MetabolicAdaptationLogCommandServiceImpl implements MetabolicAdaptationLogCommandService {

    private final MetabolicAdaptationLogRepository repository;

    public MetabolicAdaptationLogCommandServiceImpl(MetabolicAdaptationLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<MetabolicAdaptationLog, MetabolicAdaptationLogCommandFailure> handle(RecordMetabolicAdaptationCommand command) {
        try {
            MetabolicAdaptationLog log = MetabolicAdaptationLog.create(
                    command.userId(), command.previousBMR(), command.newBMR(),
                    command.previousTDEE(), command.newTDEE(), command.reason(), command.triggeredBy(),
                    command.previousCalories(), command.newCalories(),
                    command.previousProtein(), command.newProtein(),
                    command.previousCarbs(), command.newCarbs(),
                    command.previousFat(), command.newFat());
            return Result.success(repository.save(log));
        } catch (Exception ex) {
            return Result.failure(new MetabolicAdaptationLogCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
