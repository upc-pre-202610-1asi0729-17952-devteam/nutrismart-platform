package com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.entities.PaymentMethodJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad {@link PaymentMethodJpaEntity}.
 * Extiende {@link JpaRepository} con un método de búsqueda adicional
 * para consultar métodos de pago por usuario.
 */
public interface SpringDataPaymentMethodJpaRepository extends JpaRepository<PaymentMethodJpaEntity, Long> {

    List<PaymentMethodJpaEntity> findByUserId(Long userId);
}
