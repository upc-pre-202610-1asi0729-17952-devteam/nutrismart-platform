package com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.behavioralconsistency.infrastructure.persistence.jpa.entities.EatingBehaviorPatternJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad {@code EatingBehaviorPatternJpaEntity}.
 * Extiende {@code JpaRepository} y añade la consulta derivada por identificador de usuario.
 */
public interface SpringDataEatingBehaviorPatternJpaRepository extends JpaRepository<EatingBehaviorPatternJpaEntity, Long> {
    /**
     * Busca el patrón de comportamiento alimentario asociado a un usuario.
     *
     * @param userId identificador del usuario
     * @return {@code Optional} con la entidad si existe, vacío en caso contrario
     */
    Optional<EatingBehaviorPatternJpaEntity> findByUserId(Long userId);
}
