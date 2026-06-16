package com.devteam.nutrismart.platform.smartrecommendation.application.internal.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecommendationCardCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecommendationCardCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateRecommendationCardCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationCard;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.RecommendationCardRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de comandos para tarjetas de recomendación.
 * <p>
 * Gestiona la operación de escritura del agregado {@code RecommendationCard}:
 * creación de una nueva tarjeta de recomendación a partir de los datos proporcionados
 * por el comando, incluyendo insignia, tipo de clima, ciudad de viaje, tipo de tarjeta,
 * alimento asociado y descripciones en inglés y español.
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS.
 * </p>
 */
@Service
@Transactional
public class RecommendationCardCommandServiceImpl implements RecommendationCardCommandService {

    private final RecommendationCardRepository cardRepository;

    public RecommendationCardCommandServiceImpl(RecommendationCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Result<RecommendationCard, RecommendationCardCommandFailure> handle(CreateRecommendationCardCommand command) {
        try {
            RecommendationCard card = RecommendationCard.create(
                    command.badge(),
                    command.weatherType(),
                    command.travelCity(),
                    command.cardType(),
                    command.foodId(),
                    command.description(),
                    command.descriptionEs()
            );
            return Result.success(cardRepository.save(card));
        } catch (Exception ex) {
            return Result.failure(new RecommendationCardCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
