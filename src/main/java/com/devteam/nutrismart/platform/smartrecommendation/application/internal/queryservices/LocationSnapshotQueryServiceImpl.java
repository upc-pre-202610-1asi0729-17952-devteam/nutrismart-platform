package com.devteam.nutrismart.platform.smartrecommendation.application.internal.queryservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllLocationSnapshotsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetLastLocationSnapshotByUserIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetLocationSnapshotByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.LocationSnapshotQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.LocationSnapshot;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.LocationSnapshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de consultas para instantáneas de ubicación.
 * <p>
 * Gestiona las operaciones de lectura del agregado {@code LocationSnapshot}:
 * búsqueda de un snapshot por identificador, listado filtrado por usuario
 * (o de todos los registros si no se indica usuario), y consulta del último
 * snapshot registrado para un usuario ordenado por fecha de registro descendente.
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS. Todas las operaciones
 * son de solo lectura ({@code readOnly = true}).
 * </p>
 */
@Service
@Transactional(readOnly = true)
public class LocationSnapshotQueryServiceImpl implements LocationSnapshotQueryService {

    private final LocationSnapshotRepository locationSnapshotRepository;

    public LocationSnapshotQueryServiceImpl(LocationSnapshotRepository locationSnapshotRepository) {
        this.locationSnapshotRepository = locationSnapshotRepository;
    }

    @Override
    public Optional<LocationSnapshot> handle(GetLocationSnapshotByIdQuery query) {
        return locationSnapshotRepository.findById(query.id());
    }

    @Override
    public List<LocationSnapshot> handle(GetAllLocationSnapshotsQuery query) {
        if (query.userId() != null) {
            return locationSnapshotRepository.findByUserId(query.userId());
        }
        return locationSnapshotRepository.findAll();
    }

    @Override
    public Optional<LocationSnapshot> handle(GetLastLocationSnapshotByUserIdQuery query) {
        return locationSnapshotRepository.findTopByUserIdOrderByRecordedAtDesc(query.userId());
    }
}
