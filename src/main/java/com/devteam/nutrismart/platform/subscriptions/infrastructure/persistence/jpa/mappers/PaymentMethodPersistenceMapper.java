package com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.PaymentMethod;
import com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.entities.PaymentMethodJpaEntity;

/**
 * Mapeador estático que convierte entre el agregado de dominio {@link PaymentMethod}
 * y la entidad JPA {@link PaymentMethodJpaEntity}. Evita la creación de instancias
 * mediante constructor privado.
 */
public final class PaymentMethodPersistenceMapper {

    private PaymentMethodPersistenceMapper() {}

    public static PaymentMethodJpaEntity toJpaEntity(PaymentMethod paymentMethod) {
        PaymentMethodJpaEntity entity = new PaymentMethodJpaEntity();
        entity.setId(paymentMethod.getId());
        entity.setUserId(paymentMethod.getUserId());
        entity.setLastFour(paymentMethod.getLastFour());
        entity.setBrand(paymentMethod.getBrand());
        entity.setExpiryMonth(paymentMethod.getExpiryMonth());
        entity.setExpiryYear(paymentMethod.getExpiryYear());
        entity.setCardholderName(paymentMethod.getCardholderName());
        entity.setStripePaymentMethodId(paymentMethod.getStripePaymentMethodId());
        entity.setStripeCustomerId(paymentMethod.getStripeCustomerId());
        entity.setCreatedAt(paymentMethod.getCreatedAt());
        return entity;
    }

    public static PaymentMethod toDomain(PaymentMethodJpaEntity entity) {
        return PaymentMethod.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getLastFour(),
                entity.getBrand(),
                entity.getExpiryMonth(),
                entity.getExpiryYear(),
                entity.getCardholderName(),
                entity.getStripePaymentMethodId(),
                entity.getStripeCustomerId(),
                entity.getCreatedAt()
        );
    }
}
