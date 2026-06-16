package com.devteam.nutrismart.platform.smartrecommendation.application.internal.queryservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllRecommendationSessionsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetRecommendationSessionByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.RecommendationSessionQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationSession;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.RecommendationSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de consultas para sesiones de recomendación.
 * <p>
 * Gestiona las operaciones de lectura del agregado {@code RecommendationSession}:
 * búsqueda de una sesión por identificador y listado filtrado por usuario y/o
 * estado activo; si no se proporcionan filtros, devuelve todas las sesiones.
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS. Todas las operaciones
 * son de solo lectura ({@code readOnly = true}).
 * </p>
 */
@Service
@Transactional(readOnly = true)
public class RecommendationSessionQueryServiceImpl implements RecommendationSessionQueryService {

    private final RecommendationSessionRepository sessionRepository;

    public RecommendationSessionQueryServiceImpl(RecommendationSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Optional<RecommendationSession> handle(GetRecommendationSessionByIdQuery query) {
        return sessionRepository.findById(query.id());
    }

    @Override
    public List<RecommendationSession> handle(GetAllRecommendationSessionsQuery query) {
        if (query.userId() != null && query.isActive() != null) {
            return sessionRepository.findByUserIdAndIsActive(query.userId(), query.isActive());
        }
        if (query.userId() != null) {
            return sessionRepository.findByUserId(query.userId());
        }
        return sessionRepository.findAll();
    }
}
