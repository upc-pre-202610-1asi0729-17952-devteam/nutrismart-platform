package com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

/**
 * Entidad JPA que mapea el agregado {@link com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.Subscription}
 * a la tabla {@code subscriptions} de la base de datos.
 * Hereda los campos de auditoría (id, createdAt, updatedAt) de {@code AuditableAbstractPersistenceEntity}.
 */
@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
public class SubscriptionJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Identificador único del usuario propietario de la suscripción")
    @Column(unique = true, nullable = false)
    private Long userId;

    @Comment("Plan de suscripción contratado: BASIC, PRO o PREMIUM")
    @Enumerated(EnumType.STRING)
    private SubscriptionPlan plan;

    @Comment("Estado actual de la suscripción: ACTIVE, CANCELLED o EXPIRED")
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @Comment("Fecha de inicio del ciclo de facturación vigente")
    private LocalDate billingCycleStart;

    @Comment("Fecha de fin del ciclo de facturación vigente")
    private LocalDate billingCycleEnd;
}
