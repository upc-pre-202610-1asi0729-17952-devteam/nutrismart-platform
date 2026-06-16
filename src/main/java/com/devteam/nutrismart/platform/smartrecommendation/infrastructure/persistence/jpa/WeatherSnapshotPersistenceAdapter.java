package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.WeatherSnapshot;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.WeatherSnapshotRepository;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers.WeatherSnapshotPersistenceMapper;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories.SpringDataWeatherSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class WeatherSnapshotPersistenceAdapter implements WeatherSnapshotRepository {

    private final SpringDataWeatherSnapshotJpaRepository springDataRepo;

    public WeatherSnapshotPersistenceAdapter(SpringDataWeatherSnapshotJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<WeatherSnapshot> findById(Long id) {
        return springDataRepo.findById(id).map(WeatherSnapshotPersistenceMapper::toDomain);
    }

    @Override
    public List<WeatherSnapshot> findAll() {
        return springDataRepo.findAll().stream().map(WeatherSnapshotPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<WeatherSnapshot> findByCity(String city) {
        return springDataRepo.findByCity(city).stream().map(WeatherSnapshotPersistenceMapper::toDomain).toList();
    }

    @Override
    public Optional<WeatherSnapshot> findByCityAndCountry(String city, String country) {
        return springDataRepo.findByCityAndCountry(city, country).map(WeatherSnapshotPersistenceMapper::toDomain);
    }

    @Override
    public WeatherSnapshot save(WeatherSnapshot snapshot) {
        var entity = WeatherSnapshotPersistenceMapper.toJpaEntity(snapshot);
        var saved = springDataRepo.save(entity);
        return WeatherSnapshotPersistenceMapper.toDomain(saved);
    }
}
