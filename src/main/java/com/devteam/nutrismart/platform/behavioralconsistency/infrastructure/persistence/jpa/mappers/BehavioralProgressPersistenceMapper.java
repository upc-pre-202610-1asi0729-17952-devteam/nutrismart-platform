package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.BehavioralProgress;
import com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.entities.BehavioralProgressJpaEntity;

/**
 * Mapper de persistencia para el agregado {@code BehavioralProgress}.
 * Convierte entre el modelo de dominio y la entidad JPA de forma bidireccional,
 * manteniendo la separación entre las capas de dominio e infraestructura.
 */
public final class BehavioralProgressPersistenceMapper {

    private BehavioralProgressPersistenceMapper() {}

    /**
     * Convierte un agregado de dominio en una entidad JPA lista para ser persistida.
     *
     * @param domain instancia del agregado de dominio {@code BehavioralProgress}
     * @return entidad JPA {@code BehavioralProgressJpaEntity} con los datos del dominio
     */
    public static BehavioralProgressJpaEntity toJpaEntity(BehavioralProgress domain) {
        var entity = new BehavioralProgressJpaEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setAdherenceStatus(domain.getAdherenceStatus());
        entity.setStreak(domain.getStreak());
        entity.setConsecutiveMisses(domain.getConsecutiveMisses());
        entity.setWeeklyCompletionRate(domain.getWeeklyCompletionRate());
        entity.setLastEvaluatedAt(domain.getLastEvaluatedAt());
        entity.setGoalMetDates(domain.getGoalMetDates());
        return entity;
    }

    /**
     * Convierte una entidad JPA en el agregado de dominio reconstituido.
     *
     * @param entity entidad JPA {@code BehavioralProgressJpaEntity} leída de la base de datos
     * @return instancia del agregado de dominio {@code BehavioralProgress} rehidratado
     */
    public static BehavioralProgress toDomain(BehavioralProgressJpaEntity entity) {
        return BehavioralProgress.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getAdherenceStatus(),
                entity.getStreak(),
                entity.getConsecutiveMisses(),
                entity.getWeeklyCompletionRate(),
                entity.getLastEvaluatedAt(),
                entity.getGoalMetDates());
    }
}
