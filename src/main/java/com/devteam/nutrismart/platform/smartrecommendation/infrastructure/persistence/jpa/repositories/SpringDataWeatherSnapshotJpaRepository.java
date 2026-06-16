package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories;

import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.WeatherSnapshotJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataWeatherSnapshotJpaRepository extends JpaRepository<WeatherSnapshotJpaEntity, Long> {

    List<WeatherSnapshotJpaEntity> findByCity(String city);

    Optional<WeatherSnapshotJpaEntity> findByCityAndCountry(String city, String country);
}
