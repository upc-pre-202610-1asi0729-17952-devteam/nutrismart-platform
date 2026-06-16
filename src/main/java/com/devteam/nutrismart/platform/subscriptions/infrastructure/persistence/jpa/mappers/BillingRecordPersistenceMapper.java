package com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.BillingRecord;
import com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.entities.BillingRecordJpaEntity;

/**
 * Mapeador estático que convierte entre el agregado de dominio {@link BillingRecord}
 * y la entidad JPA {@link BillingRecordJpaEntity}. Evita la creación de instancias
 * mediante constructor privado.
 */
public final class BillingRecordPersistenceMapper {

    private BillingRecordPersistenceMapper() {}

    public static BillingRecordJpaEntity toJpaEntity(BillingRecord billingRecord) {
        BillingRecordJpaEntity entity = new BillingRecordJpaEntity();
        entity.setId(billingRecord.getId());
        entity.setUserId(billingRecord.getUserId());
        entity.setPlan(billingRecord.getPlan());
        entity.setAmount(billingRecord.getAmount());
        entity.setCurrency(billingRecord.getCurrency());
        entity.setPaidAt(billingRecord.getPaidAt());
        entity.setStatus(billingRecord.getStatus());
        return entity;
    }

    public static BillingRecord toDomain(BillingRecordJpaEntity entity) {
        return BillingRecord.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getPlan(),
                entity.getAmount(),
                entity.getCurrency(),
                entity.getPaidAt(),
                entity.getStatus()
        );
    }
}
