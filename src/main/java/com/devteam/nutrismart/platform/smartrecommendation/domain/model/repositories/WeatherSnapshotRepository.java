package com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.WeatherSnapshot;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio de dominio para la gestión de instantáneas meteorológicas.
 * Define el contrato de persistencia que deben implementar los adaptadores de infraestructura.
 */
public interface WeatherSnapshotRepository {

    /**
     * Busca una instantánea meteorológica por su identificador único.
     *
     * @param id identificador de la instantánea
     * @return instantánea encontrada o {@link Optional#empty()} si no existe
     */
    Optional<WeatherSnapshot> findById(Long id);

    /**
     * Devuelve todas las instantáneas meteorológicas almacenadas.
     *
     * @return lista de instantáneas meteorológicas
     */
    List<WeatherSnapshot> findAll();

    /**
     * Devuelve las instantáneas meteorológicas de una ciudad concreta.
     *
     * @param city nombre de la ciudad
     * @return lista de instantáneas de la ciudad
     */
    List<WeatherSnapshot> findByCity(String city);

    /**
     * Busca una instantánea meteorológica por ciudad y país.
     *
     * @param city    nombre de la ciudad
     * @param country código o nombre del país
     * @return instantánea encontrada o {@link Optional#empty()} si no existe
     */
    Optional<WeatherSnapshot> findByCityAndCountry(String city, String country);

    /**
     * Persiste una instantánea meteorológica nueva o actualizada.
     *
     * @param snapshot instantánea a guardar
     * @return instantánea guardada con los datos actualizados
     */
    WeatherSnapshot save(WeatherSnapshot snapshot);
}
