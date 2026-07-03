package com.devteam.nutrismart.platform.smartrecommendation.application.internal.commandservices;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceStatus;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecommendationSessionCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecommendationSessionCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateRecommendationSessionCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.UpdateRecommendationSessionCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationSession;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.events.RecommendationSessionCompletedEvent;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.events.RecommendationSessionStartedEvent;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.RecommendationSessionRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de comandos para sesiones de recomendación.
 * <p>
 * Gestiona las operaciones de escritura del agregado {@code RecommendationSession}:
 * creación de nuevas sesiones (desactivando cualquier sesión activa previa del usuario)
 * y actualización de los atributos de una sesión existente (estado de adherencia,
 * faltas consecutivas, objetivo calórico, snapshot de clima y estado activo).
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS.
 * </p>
 */
@Service
@Transactional
public class RecommendationSessionCommandServiceImpl implements RecommendationSessionCommandService {

    private final RecommendationSessionRepository sessionRepository;
    private final ApplicationEventPublisher eventPublisher;

    public RecommendationSessionCommandServiceImpl(RecommendationSessionRepository sessionRepository,
                                                   ApplicationEventPublisher eventPublisher) {
        this.sessionRepository = sessionRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<RecommendationSession, RecommendationSessionCommandFailure> handle(CreateRecommendationSessionCommand command) {
        try {
            sessionRepository.findByUserIdAndIsActive(command.userId(), true)
                    .forEach(existing -> {
                        existing.deactivate();
                        sessionRepository.save(existing);
                        eventPublisher.publishEvent(RecommendationSessionCompletedEvent.of(
                                existing.getUserId(), existing.getId()));
                    });
            AdherenceStatus adherenceStatus = AdherenceStatus.valueOf(command.adherenceStatus());
            RecommendationSession session = RecommendationSession.create(
                    command.userId(),
                    adherenceStatus,
                    command.consecutiveMisses(),
                    command.simplifiedKcalTarget(),
                    command.weatherSnapshotId()
            );
            RecommendationSession saved = sessionRepository.save(session);
            eventPublisher.publishEvent(RecommendationSessionStartedEvent.of(
                    saved.getUserId(), saved.getId()));
            return Result.success(saved);
        } catch (Exception ex) {
            return Result.failure(new RecommendationSessionCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<RecommendationSession, RecommendationSessionCommandFailure> handle(UpdateRecommendationSessionCommand command) {
        try {
            return sessionRepository.findById(command.id())
                    .map(session -> {
                        AdherenceStatus updateStatus = command.adherenceStatus() != null
                                ? AdherenceStatus.valueOf(command.adherenceStatus()) : null;
                        session.update(
                                updateStatus,
                                command.consecutiveMisses(),
                                command.simplifiedKcalTarget(),
                                command.isActive(),
                                command.weatherSnapshotId()
                        );
                        RecommendationSession saved = sessionRepository.save(session);
                        if (Boolean.FALSE.equals(saved.getIsActive())) {
                            eventPublisher.publishEvent(RecommendationSessionCompletedEvent.of(
                                    saved.getUserId(), saved.getId()));
                        }
                        return Result.<RecommendationSession, RecommendationSessionCommandFailure>success(saved);
                    })
                    .orElse(Result.failure(new RecommendationSessionCommandFailure.SessionNotFound(command.id())));
        } catch (Exception ex) {
            return Result.failure(new RecommendationSessionCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
