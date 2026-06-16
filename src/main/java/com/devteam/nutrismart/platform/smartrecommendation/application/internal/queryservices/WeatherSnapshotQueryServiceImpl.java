package com.devteam.nutrismart.platform.smartrecommendation.application.internal.queryservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllWeatherSnapshotsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetWeatherSnapshotByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.WeatherSnapshotQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.WeatherSnapshot;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.WeatherSnapshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de consultas para instantáneas meteorológicas.
 * <p>
 * Gestiona las operaciones de lectura del agregado {@code WeatherSnapshot}:
 * búsqueda de un snapshot por identificador y listado filtrado por ciudad;
 * si no se especifica ciudad, devuelve todos los snapshots registrados.
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS. Todas las operaciones
 * son de solo lectura ({@code readOnly = true}).
 * </p>
 */
@Service
@Transactional(readOnly = true)
public class WeatherSnapshotQueryServiceImpl implements WeatherSnapshotQueryService {

    private final WeatherSnapshotRepository snapshotRepository;

    public WeatherSnapshotQueryServiceImpl(WeatherSnapshotRepository snapshotRepository) {
        this.snapshotRepository = snapshotRepository;
    }

    @Override
    public Optional<WeatherSnapshot> handle(GetWeatherSnapshotByIdQuery query) {
        return snapshotRepository.findById(query.id());
    }

    @Override
    public List<WeatherSnapshot> handle(GetAllWeatherSnapshotsQuery query) {
        if (query.city() != null) {
            return snapshotRepository.findByCity(query.city());
        }
        return snapshotRepository.findAll();
    }
}
