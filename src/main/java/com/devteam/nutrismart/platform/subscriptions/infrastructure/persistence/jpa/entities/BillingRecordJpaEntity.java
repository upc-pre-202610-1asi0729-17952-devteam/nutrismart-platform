package com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.Instant;

/**
 * Entidad JPA que mapea el agregado {@link com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.BillingRecord}
 * a la tabla {@code billing_history} de la base de datos.
 * Hereda los campos de auditoría (id, createdAt, updatedAt) de {@code AuditableAbstractPersistenceEntity}.
 */
@Entity
@Table(name = "billing_history")
@Getter
@Setter
@NoArgsConstructor
public class BillingRecordJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Identificador del usuario que realizó el pago")
    @Column(nullable = false)
    private Long userId;

    @Comment("Plan de suscripción al que corresponde el pago: BASIC, PRO o PREMIUM")
    @Enumerated(EnumType.STRING)
    private SubscriptionPlan plan;

    @Comment("Monto pagado en la moneda indicada")
    private Double amount;

    @Comment("Código ISO de la moneda del pago (por ejemplo, USD, PEN)")
    private String currency;

    @Comment("Instante en que se procesó y registró el pago")
    private Instant paidAt;

    @Comment("Estado del pago (por ejemplo, PAID, PENDING, FAILED)")
    private String status;
}
