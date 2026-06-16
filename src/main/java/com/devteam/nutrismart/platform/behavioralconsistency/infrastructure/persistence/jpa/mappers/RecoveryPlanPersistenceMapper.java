package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.RecoveryPlan;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.RecoveryActionType;
import com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.entities.RecoveryPlanJpaEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper de persistencia para el agregado {@code RecoveryPlan}.
 * Convierte entre el modelo de dominio y la entidad JPA de forma bidireccional.
 * Las acciones de recuperación se serializan como cadena CSV en la base de datos.
 */
public final class RecoveryPlanPersistenceMapper {

    private RecoveryPlanPersistenceMapper() {}

    /**
     * Convierte un agregado de dominio en una entidad JPA lista para ser persistida.
     * Las acciones de recuperación se serializan como cadena CSV.
     *
     * @param domain instancia del agregado de dominio {@code RecoveryPlan}
     * @return entidad JPA {@code RecoveryPlanJpaEntity} con los datos del dominio
     */
    public static RecoveryPlanJpaEntity toJpaEntity(RecoveryPlan domain) {
        var entity = new RecoveryPlanJpaEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setStatus(domain.getStatus());
        entity.setTrigger(domain.getTrigger());
        entity.setActions(domain.getActions().stream().map(Enum::name).collect(Collectors.joining(",")));
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setExpiresAt(domain.getExpiresAt());
        return entity;
    }

    /**
     * Convierte una entidad JPA en el agregado de dominio reconstituido.
     * La cadena CSV de acciones es deserializada a la lista de {@code RecoveryActionType}.
     *
     * @param entity entidad JPA {@code RecoveryPlanJpaEntity} leída de la base de datos
     * @return instancia del agregado de dominio {@code RecoveryPlan} rehidratado
     */
    public static RecoveryPlan toDomain(RecoveryPlanJpaEntity entity) {
        return RecoveryPlan.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getStatus(),
                entity.getTrigger(),
                parseActions(entity.getActions()),
                entity.getCreatedAt(),
                entity.getExpiresAt());
    }

    private static List<RecoveryActionType> parseActions(String csv) {
        if (csv == null || csv.isBlank()) return Collections.emptyList();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .map(RecoveryActionType::valueOf)
                .toList();
    }
}
