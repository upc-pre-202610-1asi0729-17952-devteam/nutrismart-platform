package com.devteam.nutrismart.platform.smartrecommendation.application.internal.queryservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllTravelContextsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetTravelContextByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.TravelContextQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.TravelContext;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.TravelContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de consultas para contextos de viaje.
 * <p>
 * Gestiona las operaciones de lectura del agregado {@code TravelContext}:
 * búsqueda de un contexto de viaje por identificador y listado filtrado por usuario;
 * si no se especifica usuario, devuelve todos los contextos de viaje registrados.
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS. Todas las operaciones
 * son de solo lectura ({@code readOnly = true}).
 * </p>
 */
@Service
@Transactional(readOnly = true)
public class TravelContextQueryServiceImpl implements TravelContextQueryService {

    private final TravelContextRepository travelContextRepository;

    public TravelContextQueryServiceImpl(TravelContextRepository travelContextRepository) {
        this.travelContextRepository = travelContextRepository;
    }

    @Override
    public Optional<TravelContext> handle(GetTravelContextByIdQuery query) {
        return travelContextRepository.findById(query.id());
    }

    @Override
    public List<TravelContext> handle(GetAllTravelContextsQuery query) {
        if (query.userId() != null) {
            return travelContextRepository.findByUserId(query.userId());
        }
        return travelContextRepository.findAll();
    }
}
