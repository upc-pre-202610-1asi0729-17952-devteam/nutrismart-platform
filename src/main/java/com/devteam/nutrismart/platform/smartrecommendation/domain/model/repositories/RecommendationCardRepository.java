package com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationCard;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio de dominio para la gestión de tarjetas de recomendación.
 * Define el contrato de persistencia que deben implementar los adaptadores de infraestructura.
 */
public interface RecommendationCardRepository {

    /**
     * Busca una tarjeta de recomendación por su identificador único.
     *
     * @param id identificador de la tarjeta
     * @return tarjeta encontrada o {@link Optional#empty()} si no existe
     */
    Optional<RecommendationCard> findById(Long id);

    /**
     * Devuelve todas las tarjetas de recomendación almacenadas.
     *
     * @return lista de tarjetas de recomendación
     */
    List<RecommendationCard> findAll();

    /**
     * Devuelve las tarjetas de recomendación que cumplen los filtros indicados.
     * Los parámetros nulos se ignoran en el filtro.
     *
     * @param weatherType tipo de clima (puede ser nulo)
     * @param cardType    tipo de tarjeta (puede ser nulo)
     * @param travelCity  ciudad de viaje (puede ser nula)
     * @return lista de tarjetas que coinciden con los filtros
     */
    List<RecommendationCard> findByFilters(WeatherType weatherType, String cardType, String travelCity);

    /**
     * Persiste una tarjeta de recomendación nueva o actualizada.
     *
     * @param card tarjeta a guardar
     * @return tarjeta guardada con los datos actualizados
     */
    RecommendationCard save(RecommendationCard card);

    /**
     * Verifica si ya existe una tarjeta de recomendación con la misma combinación de alimento,
     * tipo, clima y ciudad de viaje.
     *
     * @param foodId      identificador del alimento
     * @param cardType    tipo de tarjeta
     * @param weatherType tipo de clima
     * @param travelCity  ciudad de viaje
     * @return {@code true} si ya existe una tarjeta con esos parámetros, {@code false} en caso contrario
     */
    boolean existsByFoodIdAndCardTypeAndWeatherTypeAndTravelCity(Long foodId, String cardType, WeatherType weatherType, String travelCity);
}
