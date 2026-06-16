package com.devteam.nutrismart.platform.metabolicadaptation.application.internal.queryservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllWearableConnectionsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetWearableConnectionByIdQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.WearableConnectionQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.WearableConnection;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.WearableConnectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de solo lectura del servicio de consultas para el agregado {@code WearableConnection}.
 * Soporta búsqueda por identificador y listado con filtro opcional por usuario.
 */
@Service
@Transactional(readOnly = true)
public class WearableConnectionQueryServiceImpl implements WearableConnectionQueryService {

    private final WearableConnectionRepository repository;

    public WearableConnectionQueryServiceImpl(WearableConnectionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<WearableConnection> handle(GetWearableConnectionByIdQuery query) {
        return repository.findById(query.id());
    }

    @Override
    public List<WearableConnection> handle(GetAllWearableConnectionsQuery query) {
        if (query.userId() != null) return repository.findAllByUserId(query.userId());
        return repository.findAll();
    }
}
