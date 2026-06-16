package com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.Subscription;
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.SubscriptionRepository;
import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.mappers.SubscriptionPersistenceMapper;
import com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.repositories.SpringDataSubscriptionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de persistencia que implementa el repositorio de dominio {@link SubscriptionRepository}
 * utilizando Spring Data JPA. Traduce entre el modelo de dominio {@code Subscription}
 * y la entidad JPA {@code SubscriptionJpaEntity} mediante el mapper de persistencia.
 */
@Component
public class SubscriptionPersistenceAdapter implements SubscriptionRepository {

    private final SpringDataSubscriptionJpaRepository springDataRepo;

    public SubscriptionPersistenceAdapter(SpringDataSubscriptionJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return springDataRepo.findById(id).map(SubscriptionPersistenceMapper::toDomain);
    }

    @Override
    public List<Subscription> findAll() {
        return springDataRepo.findAll().stream()
                .map(SubscriptionPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Subscription> findByUserId(Long userId) {
        return springDataRepo.findByUserId(userId).stream()
                .map(SubscriptionPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Subscription> findByUserIdAndStatus(Long userId, SubscriptionStatus status) {
        return springDataRepo.findByUserIdAndStatus(userId, status)
                .map(SubscriptionPersistenceMapper::toDomain);
    }

    @Override
    public Subscription save(Subscription subscription) {
        var entity = SubscriptionPersistenceMapper.toJpaEntity(subscription);
        var saved = springDataRepo.save(entity);
        return SubscriptionPersistenceMapper.toDomain(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataRepo.existsById(id);
    }
}
