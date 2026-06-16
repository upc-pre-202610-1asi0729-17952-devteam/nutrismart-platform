package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities.BodyMetricJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad {@link BodyMetricJpaEntity}.
 * Extiende {@link JpaRepository} para heredar las operaciones CRUD estándar.
 */
public interface SpringDataBodyMetricJpaRepository extends JpaRepository<BodyMetricJpaEntity, Long> {
    /**
     * Retorna todos los registros de métricas corporales de un usuario específico.
     *
     * @param userId identificador del usuario
     * @return lista de entidades del usuario
     */
    List<BodyMetricJpaEntity> findByUserId(Long userId);
}
