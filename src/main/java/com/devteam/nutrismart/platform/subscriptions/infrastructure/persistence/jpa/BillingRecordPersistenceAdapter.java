package com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.BillingRecord;
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.BillingRecordRepository;
import com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.mappers.BillingRecordPersistenceMapper;
import com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.repositories.SpringDataBillingRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de persistencia que implementa el repositorio de dominio {@link BillingRecordRepository}
 * utilizando Spring Data JPA. Traduce entre el modelo de dominio {@code BillingRecord}
 * y la entidad JPA {@code BillingRecordJpaEntity} mediante el mapper de persistencia.
 */
@Component
public class BillingRecordPersistenceAdapter implements BillingRecordRepository {

    private final SpringDataBillingRecordJpaRepository springDataRepo;

    public BillingRecordPersistenceAdapter(SpringDataBillingRecordJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<BillingRecord> findById(Long id) {
        return springDataRepo.findById(id).map(BillingRecordPersistenceMapper::toDomain);
    }

    @Override
    public List<BillingRecord> findAll() {
        return springDataRepo.findAll().stream()
                .map(BillingRecordPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<BillingRecord> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).stream()
                .map(BillingRecordPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public BillingRecord save(BillingRecord billingRecord) {
        var entity = BillingRecordPersistenceMapper.toJpaEntity(billingRecord);
        var saved = springDataRepo.save(entity);
        return BillingRecordPersistenceMapper.toDomain(saved);
    }
}
