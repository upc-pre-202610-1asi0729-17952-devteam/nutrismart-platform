package com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.entities.SubscriptionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad {@link SubscriptionJpaEntity}.
 * Extiende {@link JpaRepository} con métodos de búsqueda adicionales
 * para consultar suscripciones por usuario y estado.
 */
public interface SpringDataSubscriptionJpaRepository extends JpaRepository<SubscriptionJpaEntity, Long> {

    List<SubscriptionJpaEntity> findByUserId(Long userId);

    Optional<SubscriptionJpaEntity> findByUserIdAndStatus(Long userId, SubscriptionStatus status);
}
