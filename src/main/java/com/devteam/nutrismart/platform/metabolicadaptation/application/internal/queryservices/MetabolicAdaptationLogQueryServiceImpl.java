package com.devteam.nutrismart.platform.metabolicadaptation.application.internal.queryservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllMetabolicAdaptationLogsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.MetabolicAdaptationLogQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.MetabolicAdaptationLog;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.MetabolicAdaptationLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de solo lectura del servicio de consultas para el agregado {@code MetabolicAdaptationLog}.
 * Soporta listado completo o filtrado por usuario del historial de adaptación metabólica.
 */
@Service
@Transactional(readOnly = true)
public class MetabolicAdaptationLogQueryServiceImpl implements MetabolicAdaptationLogQueryService {

    private final MetabolicAdaptationLogRepository repository;

    public MetabolicAdaptationLogQueryServiceImpl(MetabolicAdaptationLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MetabolicAdaptationLog> handle(GetAllMetabolicAdaptationLogsQuery query) {
        if (query.userId() != null) return repository.findByUserId(query.userId());
        return repository.findAll();
    }
}
