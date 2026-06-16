package com.devteam.nutrismart.platform.behavioralconsistency.application.internal.commandservices;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.CreateEatingBehaviorPatternCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.UpdateEatingBehaviorPatternCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices.EatingBehaviorPatternCommandFailure;
import com.devteam.nutrismart.platform.behavioralconsistency.application.commandservices.EatingBehaviorPatternCommandService;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.EatingBehaviorPattern;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.repositories.EatingBehaviorPatternRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
/**
 * Implementación del servicio de comandos para los patrones de comportamiento alimentario.
 * Orquesta la creación y actualización del agregado {@code EatingBehaviorPattern}
 * delegando la persistencia al repositorio de dominio correspondiente.
 */
public class EatingBehaviorPatternCommandServiceImpl implements EatingBehaviorPatternCommandService {

    private final EatingBehaviorPatternRepository repository;

    public EatingBehaviorPatternCommandServiceImpl(EatingBehaviorPatternRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<EatingBehaviorPattern, EatingBehaviorPatternCommandFailure> handle(CreateEatingBehaviorPatternCommand command) {
        try {
            var pattern = EatingBehaviorPattern.create(command.userId(), command.weeklyCompletionRate(), command.streak());
            var saved = repository.save(pattern);
            return Result.success(saved);
        } catch (Exception ex) {
            return Result.failure(new EatingBehaviorPatternCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<EatingBehaviorPattern, EatingBehaviorPatternCommandFailure> handle(UpdateEatingBehaviorPatternCommand command) {
        try {
            return repository.findById(command.id())
                    .map(pattern -> {
                        pattern.update(command.patternType());
                        var saved = repository.save(pattern);
                        return Result.<EatingBehaviorPattern, EatingBehaviorPatternCommandFailure>success(saved);
                    })
                    .orElse(Result.failure(new EatingBehaviorPatternCommandFailure.EatingBehaviorPatternNotFound(command.id())));
        } catch (Exception ex) {
            return Result.failure(new EatingBehaviorPatternCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
