package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.WeatherSnapshot;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.WeatherSnapshotJpaEntity;

public final class WeatherSnapshotPersistenceMapper {

    private WeatherSnapshotPersistenceMapper() {}

    public static WeatherSnapshotJpaEntity toJpaEntity(WeatherSnapshot snapshot) {
        WeatherSnapshotJpaEntity entity = new WeatherSnapshotJpaEntity();
        entity.setId(snapshot.getId());
        entity.setCity(snapshot.getCity());
        entity.setCountry(snapshot.getCountry());
        entity.setTemperatureCelsius(snapshot.getTemperatureCelsius());
        entity.setCondition(snapshot.getCondition());
        entity.setWeatherType(snapshot.getWeatherType());
        entity.setUpdatedAt(snapshot.getUpdatedAt());
        return entity;
    }

    public static WeatherSnapshot toDomain(WeatherSnapshotJpaEntity entity) {
        return WeatherSnapshot.rehydrate(
                entity.getId(),
                entity.getCity(),
                entity.getCountry(),
                entity.getTemperatureCelsius(),
                entity.getCondition(),
                entity.getWeatherType(),
                entity.getUpdatedAt()
        );
    }
}
