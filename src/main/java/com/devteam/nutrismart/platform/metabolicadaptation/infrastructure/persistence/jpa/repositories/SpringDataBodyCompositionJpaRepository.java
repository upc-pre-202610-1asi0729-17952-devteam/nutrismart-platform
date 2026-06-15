package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities.BodyCompositionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad {@link BodyCompositionJpaEntity}.
 * Extiende {@link JpaRepository} para heredar las operaciones CRUD estándar.
 */
public interface SpringDataBodyCompositionJpaRepository extends JpaRepository<BodyCompositionJpaEntity, Long> {
    /**
     * Retorna la primera medición de composición corporal de un usuario.
     *
     * @param userId identificador del usuario
     * @return {@link Optional} con la entidad si existe
     */
    Optional<BodyCompositionJpaEntity> findByUserId(Long userId);

    /**
     * Retorna todas las mediciones de composición corporal de un usuario.
     *
     * @param userId identificador del usuario
     * @return lista de entidades del usuario
     */
    List<BodyCompositionJpaEntity> findAllByUserId(Long userId);
}
