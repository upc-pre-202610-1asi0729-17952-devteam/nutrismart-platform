package com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserGoal;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecipeSuggestion;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio de dominio para la gestión de sugerencias de recetas.
 * Define el contrato de persistencia que deben implementar los adaptadores de infraestructura.
 */
public interface RecipeSuggestionRepository {

    /**
     * Busca una sugerencia de receta por su identificador único.
     *
     * @param id identificador de la receta
     * @return receta encontrada o {@link Optional#empty()} si no existe
     */
    Optional<RecipeSuggestion> findById(Long id);

    /**
     * Devuelve todas las sugerencias de recetas almacenadas.
     *
     * @return lista de sugerencias de recetas
     */
    List<RecipeSuggestion> findAll();

    /**
     * Devuelve las sugerencias de recetas asociadas a un objetivo nutricional.
     *
     * @param goalType objetivo nutricional del usuario
     * @return lista de recetas filtradas por objetivo
     */
    List<RecipeSuggestion> findByGoalType(UserGoal goalType);

    /**
     * Persiste una sugerencia de receta nueva o actualizada.
     *
     * @param recipe receta a guardar
     * @return receta guardada con los datos actualizados
     */
    RecipeSuggestion save(RecipeSuggestion recipe);

    /**
     * Persiste una lista de sugerencias de recetas en bloque.
     *
     * @param recipes lista de recetas a guardar
     * @return lista de recetas guardadas con los datos actualizados
     */
    List<RecipeSuggestion> saveAll(List<RecipeSuggestion> recipes);

    /**
     * Verifica si ya existe una receta con el nombre indicado (sin distinción de mayúsculas).
     *
     * @param name nombre de la receta a verificar
     * @return {@code true} si existe una receta con ese nombre, {@code false} en caso contrario
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Devuelve el número total de sugerencias de recetas almacenadas.
     *
     * @return total de recetas en el repositorio
     */
    long count();
}
