package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.EatingBehaviorPattern;
import com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.entities.EatingBehaviorPatternJpaEntity;

/**
 * Mapper de persistencia para el agregado {@code EatingBehaviorPattern}.
 * Convierte entre el modelo de dominio y la entidad JPA de forma bidireccional,
 * manteniendo la separación entre las capas de dominio e infraestructura.
 */
public final class EatingBehaviorPatternPersistenceMapper {

    private EatingBehaviorPatternPersistenceMapper() {}

    /**
     * Convierte un agregado de dominio en una entidad JPA lista para ser persistida.
     *
     * @param domain instancia del agregado de dominio {@code EatingBehaviorPattern}
     * @return entidad JPA {@code EatingBehaviorPatternJpaEntity} con los datos del dominio
     */
    public static EatingBehaviorPatternJpaEntity toJpaEntity(EatingBehaviorPattern domain) {
        var entity = new EatingBehaviorPatternJpaEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setPatternType(domain.getPatternType());
        entity.setDetectedAt(domain.getDetectedAt());
        return entity;
    }

    /**
     * Convierte una entidad JPA en el agregado de dominio reconstituido.
     *
     * @param entity entidad JPA {@code EatingBehaviorPatternJpaEntity} leída de la base de datos
     * @return instancia del agregado de dominio {@code EatingBehaviorPattern} rehidratado
     */
    public static EatingBehaviorPattern toDomain(EatingBehaviorPatternJpaEntity entity) {
        return EatingBehaviorPattern.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getPatternType(),
                entity.getDetectedAt());
    }
}
