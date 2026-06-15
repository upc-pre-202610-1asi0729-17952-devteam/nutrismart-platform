package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyMetric;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio para la persistencia del agregado {@link BodyMetric}.
 * Define las operaciones de acceso a datos requeridas por la capa de aplicación.
 */
public interface BodyMetricRepository {
    /**
     * Busca un registro de métricas corporales por su identificador.
     *
     * @param id identificador del registro
     * @return {@link Optional} con el agregado si existe, vacío en caso contrario
     */
    Optional<BodyMetric> findById(Long id);

    /**
     * Retorna todos los registros de métricas corporales almacenados.
     *
     * @return lista de todos los {@link BodyMetric}
     */
    List<BodyMetric> findAll();

    /**
     * Retorna los registros de métricas corporales asociados a un usuario específico.
     *
     * @param userId identificador del usuario
     * @return lista de {@link BodyMetric} del usuario
     */
    List<BodyMetric> findByUserId(Long userId);

    /**
     * Persiste o actualiza un registro de métricas corporales.
     *
     * @param bodyMetric agregado a guardar
     * @return instancia persistida con identificador asignado
     */
    BodyMetric save(BodyMetric bodyMetric);

    /**
     * Elimina el registro de métricas corporales con el identificador indicado.
     *
     * @param id identificador del registro a eliminar
     */
    void deleteById(Long id);

    /**
     * Verifica si existe un registro de métricas corporales con el identificador dado.
     *
     * @param id identificador a verificar
     * @return {@code true} si existe, {@code false} en caso contrario
     */
    boolean existsById(Long id);
}
