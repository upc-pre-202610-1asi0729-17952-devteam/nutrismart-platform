package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyComposition;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio para la persistencia del agregado {@link BodyComposition}.
 * Define las operaciones de acceso a datos requeridas por la capa de aplicación.
 */
public interface BodyCompositionRepository {
    /**
     * Busca un registro de composición corporal por su identificador.
     *
     * @param id identificador del registro
     * @return {@link Optional} con el agregado si existe, vacío en caso contrario
     */
    Optional<BodyComposition> findById(Long id);

    /**
     * Retorna todos los registros de composición corporal almacenados.
     *
     * @return lista de todos los {@link BodyComposition}
     */
    List<BodyComposition> findAll();

    /**
     * Retorna el registro de composición corporal más reciente de un usuario.
     *
     * @param userId identificador del usuario
     * @return {@link Optional} con el registro si existe, vacío en caso contrario
     */
    Optional<BodyComposition> findByUserId(Long userId);

    /**
     * Retorna todos los registros de composición corporal de un usuario.
     *
     * @param userId identificador del usuario
     * @return lista de {@link BodyComposition} del usuario
     */
    List<BodyComposition> findAllByUserId(Long userId);

    /**
     * Persiste o actualiza un registro de composición corporal.
     *
     * @param bodyComposition agregado a guardar
     * @return instancia persistida con identificador asignado
     */
    BodyComposition save(BodyComposition bodyComposition);

    /**
     * Elimina el registro de composición corporal con el identificador indicado.
     *
     * @param id identificador del registro a eliminar
     */
    void deleteById(Long id);

    /**
     * Verifica si existe un registro de composición corporal con el identificador dado.
     *
     * @param id identificador a verificar
     * @return {@code true} si existe, {@code false} en caso contrario
     */
    boolean existsById(Long id);
}
