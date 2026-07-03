package com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.PaymentMethod;
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.PaymentMethodRepository;
import com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.entities.PaymentMethodJpaEntity;
import com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.mappers.PaymentMethodPersistenceMapper;
import com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.repositories.SpringDataPaymentMethodJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de persistencia que implementa el repositorio de dominio {@link PaymentMethodRepository}
 * utilizando Spring Data JPA. Traduce entre el modelo de dominio {@code PaymentMethod}
 * y la entidad JPA {@code PaymentMethodJpaEntity} mediante el mapper de persistencia.
 */
@Component
public class PaymentMethodPersistenceAdapter implements PaymentMethodRepository {

    private final SpringDataPaymentMethodJpaRepository springDataRepo;

    public PaymentMethodPersistenceAdapter(SpringDataPaymentMethodJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<PaymentMethod> findById(Long id) {
        return springDataRepo.findById(id).map(PaymentMethodPersistenceMapper::toDomain);
    }

    @Override
    public List<PaymentMethod> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).stream()
                .map(PaymentMethodPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<String> findAnyStripeCustomerIdByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).stream()
                .map(PaymentMethodJpaEntity::getStripeCustomerId)
                .filter(id -> id != null && !id.isBlank())
                .findFirst();
    }

    @Override
    public PaymentMethod save(PaymentMethod paymentMethod) {
        var entity = PaymentMethodPersistenceMapper.toJpaEntity(paymentMethod);
        var saved = springDataRepo.save(entity);
        return PaymentMethodPersistenceMapper.toDomain(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataRepo.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        springDataRepo.deleteById(id);
    }
}
