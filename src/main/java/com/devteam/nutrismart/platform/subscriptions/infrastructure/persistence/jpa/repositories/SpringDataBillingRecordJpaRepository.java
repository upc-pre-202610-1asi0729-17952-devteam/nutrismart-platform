package com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.subscriptions.infrastructure.persistence.jpa.entities.BillingRecordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad {@link BillingRecordJpaEntity}.
 * Extiende {@link JpaRepository} con un método de búsqueda adicional
 * para consultar registros de pago por usuario.
 */
public interface SpringDataBillingRecordJpaRepository extends JpaRepository<BillingRecordJpaEntity, Long> {

    List<BillingRecordJpaEntity> findByUserId(Long userId);
}
