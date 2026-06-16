package com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.Subscription;
import com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.entities.SubscriptionJpaEntity;

/**
 * Mapeador estático que convierte entre el agregado de dominio {@link Subscription}
 * y la entidad JPA {@link SubscriptionJpaEntity}. Evita la creación de instancias
 * mediante constructor privado.
 */
public final class SubscriptionPersistenceMapper {

    private SubscriptionPersistenceMapper() {}

    public static SubscriptionJpaEntity toJpaEntity(Subscription subscription) {
        SubscriptionJpaEntity entity = new SubscriptionJpaEntity();
        entity.setId(subscription.getId());
        entity.setUserId(subscription.getUserId());
        entity.setPlan(subscription.getPlan());
        entity.setStatus(subscription.getStatus());
        entity.setBillingCycleStart(subscription.getBillingCycleStart());
        entity.setBillingCycleEnd(subscription.getBillingCycleEnd());
        entity.setCreatedAt(subscription.getCreatedAt());
        return entity;
    }

    public static Subscription toDomain(SubscriptionJpaEntity entity) {
        return Subscription.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getPlan(),
                entity.getStatus(),
                entity.getBillingCycleStart(),
                entity.getBillingCycleEnd(),
                entity.getCreatedAt()
        );
    }
}
