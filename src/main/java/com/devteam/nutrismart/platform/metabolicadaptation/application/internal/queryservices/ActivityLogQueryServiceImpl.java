package com.devteam.nutrismart.platform.metabolicadaptation.application.internal.queryservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetActivityLogByIdQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllActivityLogsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.ActivityLogQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.ActivityLog;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.ActivityLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de solo lectura del servicio de consultas para el agregado {@code ActivityLog}.
 * Soporta búsqueda por identificador y listado con filtro opcional por usuario.
 */
@Service
@Transactional(readOnly = true)
public class ActivityLogQueryServiceImpl implements ActivityLogQueryService {

    private final ActivityLogRepository repository;

    public ActivityLogQueryServiceImpl(ActivityLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ActivityLog> handle(GetActivityLogByIdQuery query) {
        return repository.findById(query.id());
    }

    @Override
    public List<ActivityLog> handle(GetAllActivityLogsQuery query) {
        if (query.userId() != null) return repository.findByUserId(query.userId());
        return repository.findAll();
    }
}
