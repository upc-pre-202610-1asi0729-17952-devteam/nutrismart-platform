package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.RecommendationCardJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataRecommendationCardJpaRepository extends JpaRepository<RecommendationCardJpaEntity, Long> {

    @Query("SELECT rc FROM RecommendationCardJpaEntity rc WHERE " +
           "(:weatherType IS NULL OR rc.weatherType = :weatherType) AND " +
           "(:cardType IS NULL OR rc.cardType = :cardType) AND " +
           "(:travelCity IS NULL OR rc.travelCity = :travelCity)")
    List<RecommendationCardJpaEntity> findByFilters(
            @Param("weatherType") WeatherType weatherType,
            @Param("cardType") String cardType,
            @Param("travelCity") String travelCity);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM RecommendationCardJpaEntity e " +
           "WHERE e.foodId = :foodId AND e.cardType = :cardType " +
           "AND ((:weatherType IS NULL AND e.weatherType IS NULL) OR e.weatherType = :weatherType) " +
           "AND ((:travelCity IS NULL AND e.travelCity IS NULL) OR e.travelCity = :travelCity)")
    boolean existsByFoodIdAndCardTypeAndWeatherTypeAndTravelCity(
            @Param("foodId") Long foodId,
            @Param("cardType") String cardType,
            @Param("weatherType") WeatherType weatherType,
            @Param("travelCity") String travelCity);
}
