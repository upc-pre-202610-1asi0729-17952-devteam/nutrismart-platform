package com.devteam.nutrismart.platform.smartrecommendation.application.internal.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.LocationSnapshotCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.LocationSnapshotCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateLocationSnapshotCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.LocationSnapshot;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.LocationSnapshotRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de comandos para instantáneas de ubicación.
 * <p>
 * Gestiona la operación de escritura del agregado {@code LocationSnapshot}:
 * creación de un nuevo registro de ubicación para un usuario con ciudad y país,
 * representando la posición geográfica detectada o declarada en un momento dado.
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS.
 * </p>
 */
@Service
@Transactional
public class LocationSnapshotCommandServiceImpl implements LocationSnapshotCommandService {

    private final LocationSnapshotRepository locationSnapshotRepository;

    public LocationSnapshotCommandServiceImpl(LocationSnapshotRepository locationSnapshotRepository) {
        this.locationSnapshotRepository = locationSnapshotRepository;
    }

    @Override
    public Result<LocationSnapshot, LocationSnapshotCommandFailure> handle(CreateLocationSnapshotCommand command) {
        try {
            LocationSnapshot snapshot = LocationSnapshot.create(command.userId(), command.city(), command.country());
            return Result.success(locationSnapshotRepository.save(snapshot));
        } catch (Exception ex) {
            return Result.failure(new LocationSnapshotCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
