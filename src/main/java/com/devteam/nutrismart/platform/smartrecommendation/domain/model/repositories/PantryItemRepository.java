package com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.PantryItem;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio de dominio para la gestión de ítems de despensa.
 * Define el contrato de persistencia que deben implementar los adaptadores de infraestructura.
 */
public interface PantryItemRepository {

    /**
     * Busca un ítem de despensa por su identificador único.
     *
     * @param id identificador del ítem
     * @return ítem encontrado o {@link Optional#empty()} si no existe
     */
    Optional<PantryItem> findById(Long id);

    /**
     * Devuelve todos los ítems de despensa almacenados.
     *
     * @return lista de ítems de despensa
     */
    List<PantryItem> findAll();

    /**
     * Devuelve todos los ítems de despensa pertenecientes a un usuario.
     *
     * @param userId identificador del usuario
     * @return lista de ítems del usuario
     */
    List<PantryItem> findByUserId(Long userId);

    /**
     * Persiste un ítem de despensa nuevo o actualizado.
     *
     * @param item ítem a guardar
     * @return ítem guardado con los datos actualizados
     */
    PantryItem save(PantryItem item);

    /**
     * Elimina el ítem de despensa con el identificador indicado.
     *
     * @param id identificador del ítem a eliminar
     */
    void deleteById(Long id);

    /**
     * Comprueba si existe un ítem de despensa con el identificador indicado.
     *
     * @param id identificador del ítem
     * @return {@code true} si existe, {@code false} en caso contrario
     */
    boolean existsById(Long id);
}
