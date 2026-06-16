package com.devteam.nutrismart.platform.smartrecommendation.application.internal.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.TravelContextCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.TravelContextCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateTravelContextCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.UpdateTravelContextCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.TravelContext;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.TravelContextRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de comandos para contextos de viaje.
 * <p>
 * Gestiona las operaciones de escritura del agregado {@code TravelContext}:
 * creación de un nuevo contexto de viaje para un usuario con ciudad, país e indicador
 * de configuración manual, y actualización del contexto existente (estado activo,
 * ciudad, país y fecha de activación).
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS.
 * </p>
 */
@Service
@Transactional
public class TravelContextCommandServiceImpl implements TravelContextCommandService {

    private final TravelContextRepository travelContextRepository;

    public TravelContextCommandServiceImpl(TravelContextRepository travelContextRepository) {
        this.travelContextRepository = travelContextRepository;
    }

    @Override
    public Result<TravelContext, TravelContextCommandFailure> handle(CreateTravelContextCommand command) {
        try {
            TravelContext context = TravelContext.create(
                    command.userId(),
                    command.city(),
                    command.country(),
                    command.isManual()
            );
            return Result.success(travelContextRepository.save(context));
        } catch (Exception ex) {
            return Result.failure(new TravelContextCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<TravelContext, TravelContextCommandFailure> handle(UpdateTravelContextCommand command) {
        try {
            return travelContextRepository.findById(command.id())
                    .map(context -> {
                        context.update(command.isActive(), command.city(), command.country(), command.activatedAt());
                        return Result.<TravelContext, TravelContextCommandFailure>success(travelContextRepository.save(context));
                    })
                    .orElse(Result.failure(new TravelContextCommandFailure.TravelContextNotFound(command.id())));
        } catch (Exception ex) {
            return Result.failure(new TravelContextCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
