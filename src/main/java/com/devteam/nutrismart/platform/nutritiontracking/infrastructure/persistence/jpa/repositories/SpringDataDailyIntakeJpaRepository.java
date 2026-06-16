package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.entities.DailyIntakeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad {@link DailyIntakeJpaEntity}.
 * <p>
 * Provee operaciones de acceso a datos para los registros de ingesta calórica diaria,
 * extendiendo las capacidades estándar de {@link JpaRepository} con consultas
 * específicas del dominio de seguimiento nutricional.
 * </p>
 */
public interface SpringDataDailyIntakeJpaRepository extends JpaRepository<DailyIntakeJpaEntity, Long> {

    /**
     * Recupera todos los registros de ingesta diaria asociados a un usuario específico.
     *
     * @param userId identificador único del usuario
     * @return lista de registros de ingesta diaria del usuario; vacía si no existen registros
     */
    List<DailyIntakeJpaEntity> findByUserId(Long userId);

    /**
     * Busca el registro de ingesta diaria de un usuario para una fecha concreta.
     *
     * @param userId identificador único del usuario
     * @param date   fecha del registro de ingesta a buscar
     * @return {@link Optional} con el registro encontrado, o vacío si no existe
     */
    Optional<DailyIntakeJpaEntity> findByUserIdAndDate(Long userId, LocalDate date);

    /**
     * Verifica si existe un registro de ingesta diaria para un usuario en una fecha determinada.
     *
     * @param userId identificador único del usuario
     * @param date   fecha a verificar
     * @return {@code true} si existe el registro; {@code false} en caso contrario
     */
    boolean existsByUserIdAndDate(Long userId, LocalDate date);
}
