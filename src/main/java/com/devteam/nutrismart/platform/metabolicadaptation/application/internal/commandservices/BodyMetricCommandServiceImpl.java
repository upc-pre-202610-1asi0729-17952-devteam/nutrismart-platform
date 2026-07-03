package com.devteam.nutrismart.platform.metabolicadaptation.application.internal.commandservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.BodyMetricCommandFailure;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.BodyMetricCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.LogBodyMetricsCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.UpdateBodyMetricCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyMetric;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.events.BodyMetricsRecordedEvent;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.BodyMetricRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;

/**
 * Implementación transaccional del servicio de comandos para el agregado {@code BodyMetric}.
 * Delega la persistencia al repositorio de dominio y envuelve los resultados en {@code Result}.
 */
@Service
@Transactional
public class BodyMetricCommandServiceImpl implements BodyMetricCommandService {

    private final BodyMetricRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public BodyMetricCommandServiceImpl(BodyMetricRepository repository,
                                        ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<BodyMetric, BodyMetricCommandFailure> handle(LogBodyMetricsCommand command) {
        try {
            BodyMetric bm = BodyMetric.create(command.userId(), command.weightKg(), command.heightCm(),
                    command.loggedAt(), command.targetWeightKg(), command.projectedAchievementDate());
            BodyMetric saved = repository.save(bm);
            publishRecorded(saved);
            return Result.success(saved);
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
                        BodyMetric saved = repository.save(bm);
                        publishRecorded(saved);
                        return Result.<BodyMetric, BodyMetricCommandFailure>success(saved);
                    })
                    .orElse(Result.failure(new BodyMetricCommandFailure.BodyMetricNotFound(command.id())));
        } catch (Exception ex) {
            return Result.failure(new BodyMetricCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    private void publishRecorded(BodyMetric bm) {
        eventPublisher.publishEvent(BodyMetricsRecordedEvent.of(
                bm.getUserId(), bm.getWeightKg(),
                bm.getLoggedAt().atZone(ZoneId.systemDefault()).toLocalDate()));
    }
}
