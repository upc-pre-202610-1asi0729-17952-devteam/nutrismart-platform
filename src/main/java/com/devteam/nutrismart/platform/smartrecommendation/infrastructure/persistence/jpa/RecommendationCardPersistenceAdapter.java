package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecommendationCard;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.RecommendationCardRepository;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers.RecommendationCardPersistenceMapper;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.repositories.SpringDataRecommendationCardJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecommendationCardPersistenceAdapter implements RecommendationCardRepository {

    private final SpringDataRecommendationCardJpaRepository springDataRepo;

    public RecommendationCardPersistenceAdapter(SpringDataRecommendationCardJpaRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Optional<RecommendationCard> findById(Long id) {
        return springDataRepo.findById(id).map(RecommendationCardPersistenceMapper::toDomain);
    }

    @Override
    public List<RecommendationCard> findAll() {
        return springDataRepo.findAll().stream().map(RecommendationCardPersistenceMapper::toDomain).toList();
    }

    @Override
    public List<RecommendationCard> findByFilters(WeatherType weatherType, String cardType, String travelCity) {
        return springDataRepo.findByFilters(weatherType, cardType, travelCity).stream()
                .map(RecommendationCardPersistenceMapper::toDomain).toList();
    }

    @Override
    public RecommendationCard save(RecommendationCard card) {
        var entity = RecommendationCardPersistenceMapper.toJpaEntity(card);
        var saved = springDataRepo.save(entity);
        return RecommendationCardPersistenceMapper.toDomain(saved);
    }

    @Override
    public boolean existsByFoodIdAndCardTypeAndWeatherTypeAndTravelCity(Long foodId, String cardType,
                                                                         WeatherType weatherType, String travelCity) {
        return springDataRepo.existsByFoodIdAndCardTypeAndWeatherTypeAndTravelCity(foodId, cardType, weatherType, travelCity);
    }
}
