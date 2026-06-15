package com.devteam.nutrismart.platform.metabolicadaptation.application.internal.commandservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.BodyCompositionCommandFailure;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.BodyCompositionCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.LogBodyCompositionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.UpdateBodyCompositionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyComposition;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.BodyCompositionRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación transaccional del servicio de comandos para el agregado {@code BodyComposition}.
 * Delega la persistencia al repositorio de dominio y envuelve los resultados en {@code Result}.
 */
@Service
@Transactional
public class BodyCompositionCommandServiceImpl implements BodyCompositionCommandService {

    private final BodyCompositionRepository repository;

    public BodyCompositionCommandServiceImpl(BodyCompositionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<BodyComposition, BodyCompositionCommandFailure> handle(LogBodyCompositionCommand command) {
        try {
            BodyComposition bc = BodyComposition.create(command.userId(), command.waistCm(), command.neckCm(),
                    command.heightCm(), command.weightKg(), command.measuredAt(),
                    command.previousBodyFatPercent(), command.overrideBodyFatPercent());
            return Result.success(repository.save(bc));
        } catch (Exception ex) {
            return Result.failure(new BodyCompositionCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<BodyComposition, BodyCompositionCommandFailure> handle(UpdateBodyCompositionCommand command) {
        try {
            return repository.findById(command.id())
                    .map(bc -> {
                        bc.update(command.waistCm(), command.neckCm(), command.heightCm(),
                                command.weightKg(), command.overrideBodyFatPercent());
                        return Result.<BodyComposition, BodyCompositionCommandFailure>success(repository.save(bc));
                    })
                    .orElse(Result.failure(new BodyCompositionCommandFailure.BodyCompositionNotFound(command.id())));
        } catch (Exception ex) {
            return Result.failure(new BodyCompositionCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
