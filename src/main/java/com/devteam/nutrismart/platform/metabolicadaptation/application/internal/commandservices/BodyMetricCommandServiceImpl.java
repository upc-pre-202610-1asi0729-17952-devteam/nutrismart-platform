package com.devteam.nutrismart.platform.metabolicadaptation.application.internal.commandservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.BodyMetricCommandFailure;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.BodyMetricCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.LogBodyMetricsCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.UpdateBodyMetricCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyMetric;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.BodyMetricRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación transaccional del servicio de comandos para el agregado {@code BodyMetric}.
 * Delega la persistencia al repositorio de dominio y envuelve los resultados en {@code Result}.
 */
@Service
@Transactional
public class BodyMetricCommandServiceImpl implements BodyMetricCommandService {

    private final BodyMetricRepository repository;

    public BodyMetricCommandServiceImpl(BodyMetricRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<BodyMetric, BodyMetricCommandFailure> handle(LogBodyMetricsCommand command) {
        try {
            BodyMetric bm = BodyMetric.create(command.userId(), command.weightKg(), command.heightCm(),
                    command.loggedAt(), command.targetWeightKg(), command.projectedAchievementDate());
            return Result.success(repository.save(bm));
        } catch (Exception ex) {
            return Result.failure(new BodyMetricCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<BodyMetric, BodyMetricCommandFailure> handle(UpdateBodyMetricCommand command) {
        try {
            return repository.findById(command.id())
                    .map(bm -> {
                        bm.update(command.weightKg(), command.heightCm(), command.targetWeightKg(), command.projectedAchievementDate());
                        return Result.<BodyMetric, BodyMetricCommandFailure>success(repository.save(bm));
                    })
                    .orElse(Result.failure(new BodyMetricCommandFailure.BodyMetricNotFound(command.id())));
        } catch (Exception ex) {
            return Result.failure(new BodyMetricCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
