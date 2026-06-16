package com.devteam.nutrismart.platform.smartrecommendation.application.internal.queryservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllRecommendationCardsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetRecommendationCardByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.RecommendationCardQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationCard;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.RecommendationCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de consultas para tarjetas de recomendación.
 * <p>
 * Gestiona las operaciones de lectura del agregado {@code RecommendationCard}:
 * búsqueda de una tarjeta por identificador y listado filtrado por tipo de clima,
 * tipo de tarjeta y ciudad de viaje.
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS. Todas las operaciones
 * son de solo lectura ({@code readOnly = true}).
 * </p>
 */
@Service
@Transactional(readOnly = true)
public class RecommendationCardQueryServiceImpl implements RecommendationCardQueryService {

    private final RecommendationCardRepository cardRepository;

    public RecommendationCardQueryServiceImpl(RecommendationCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Optional<RecommendationCard> handle(GetRecommendationCardByIdQuery query) {
        return cardRepository.findById(query.id());
    }

    @Override
    public List<RecommendationCard> handle(GetAllRecommendationCardsQuery query) {
        return cardRepository.findByFilters(query.weatherType(), query.cardType(), query.travelCity());
    }
}
