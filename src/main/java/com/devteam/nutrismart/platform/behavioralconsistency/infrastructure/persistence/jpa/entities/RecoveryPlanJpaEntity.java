package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceDropTrigger;
import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.RecoveryPlanStatus;
import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Entidad JPA que mapea la tabla {@code recovery_plans} en la base de datos.
 * Representa la persistencia del agregado de dominio {@code RecoveryPlan}.
 */
@Entity
@Table(name = "recovery_plans")
@Getter
@Setter
@NoArgsConstructor
public class RecoveryPlanJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Identificador del usuario al que pertenece este plan de recuperación")
    @Column(nullable = false)
    private Long userId;

    @Comment("Estado del ciclo de vida del plan: ACTIVE, COMPLETED o EXPIRED")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecoveryPlanStatus status;

    @Comment("Causa que desencadenó la caída en la adherencia y originó este plan")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "drop_trigger")
    private AdherenceDropTrigger trigger;

    @Comment("Lista de acciones de recuperación serializadas como cadena CSV separada por comas")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String actions;

    @Comment("Marca de tiempo en que este plan de recuperación vence o caduca")
    @Column(nullable = false)
    private Instant expiresAt;
}
