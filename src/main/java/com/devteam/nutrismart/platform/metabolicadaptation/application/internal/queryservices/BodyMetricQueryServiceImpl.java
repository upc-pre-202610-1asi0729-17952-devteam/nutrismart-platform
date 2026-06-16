package com.devteam.nutrismart.platform.metabolicadaptation.application.internal.queryservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllBodyMetricsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.BodyMetricQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyMetric;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.BodyMetricRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de solo lectura del servicio de consultas para el agregado {@code BodyMetric}.
 * Delega las operaciones de lectura al repositorio de dominio y aplica filtros opcionales por usuario.
 */
@Service
@Transactional(readOnly = true)
public class BodyMetricQueryServiceImpl implements BodyMetricQueryService {

    private final BodyMetricRepository repository;

    public BodyMetricQueryServiceImpl(BodyMetricRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BodyMetric> handle(GetAllBodyMetricsQuery query) {
        if (query.userId() != null) return repository.findByUserId(query.userId());
        return repository.findAll();
    }
}
