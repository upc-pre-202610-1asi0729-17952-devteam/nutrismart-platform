package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.WearableConnection;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio para la persistencia del agregado {@link WearableConnection}.
 * Define las operaciones de acceso a datos requeridas por la capa de aplicación.
 */
public interface WearableConnectionRepository {
    /**
     * Busca una conexión wearable por su identificador.
     *
     * @param id identificador del registro
     * @return {@link Optional} con el agregado si existe, vacío en caso contrario
     */
    Optional<WearableConnection> findById(Long id);

    /**
     * Retorna todas las conexiones wearable almacenadas.
     *
     * @return lista de todas las {@link WearableConnection}
     */
    List<WearableConnection> findAll();

    /**
     * Retorna la conexión wearable principal de un usuario.
     *
     * @param userId identificador del usuario
     * @return {@link Optional} con la conexión si existe, vacío en caso contrario
     */
    Optional<WearableConnection> findByUserId(Long userId);

    /**
     * Retorna todas las conexiones wearable de un usuario.
     *
     * @param userId identificador del usuario
     * @return lista de {@link WearableConnection} del usuario
     */
    List<WearableConnection> findAllByUserId(Long userId);

    /**
     * Persiste o actualiza una conexión wearable.
     *
     * @param wearableConnection agregado a guardar
     * @return instancia persistida con identificador asignado
     */
    WearableConnection save(WearableConnection wearableConnection);

    /**
     * Elimina la conexión wearable con el identificador indicado.
     *
     * @param id identificador del registro a eliminar
     */
    void deleteById(Long id);

    /**
     * Verifica si existe una conexión wearable con el identificador dado.
     *
     * @param id identificador a verificar
     * @return {@code true} si existe, {@code false} en caso contrario
     */
    boolean existsById(Long id);
}
