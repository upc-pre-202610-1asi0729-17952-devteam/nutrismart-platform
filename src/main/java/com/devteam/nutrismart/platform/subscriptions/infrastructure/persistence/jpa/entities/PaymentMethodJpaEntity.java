package com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

/**
 * Entidad JPA que mapea el agregado {@link com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.PaymentMethod}
 * a la tabla {@code payment_methods} de la base de datos.
 * Hereda los campos de auditoría (id, createdAt, updatedAt) de {@code AuditableAbstractPersistenceEntity}.
 */
@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@NoArgsConstructor
public class PaymentMethodJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Identificador del usuario propietario del método de pago")
    @Column(nullable = false)
    private Long userId;

    @Comment("Últimos cuatro dígitos de la tarjeta")
    private String lastFour;

    @Comment("Marca de la tarjeta (Visa, Mastercard, Amex, ...)")
    private String brand;

    @Comment("Mes de expiración de la tarjeta (1-12)")
    private Integer expiryMonth;

    @Comment("Año de expiración de la tarjeta (cuatro dígitos)")
    private Integer expiryYear;

    @Comment("Nombre del titular de la tarjeta")
    private String cardholderName;

    @Comment("Identificador tokenizado del método de pago en Stripe (pm_xxx)")
    @Column(nullable = false)
    private String stripePaymentMethodId;

    @Comment("Identificador del Customer de Stripe que respalda los cargos de esta tarjeta (cus_xxx)")
    private String stripeCustomerId;
}
