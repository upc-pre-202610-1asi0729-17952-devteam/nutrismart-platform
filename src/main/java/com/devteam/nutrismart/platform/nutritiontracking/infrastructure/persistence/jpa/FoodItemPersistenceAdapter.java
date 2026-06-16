package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.FoodItemRepository;
import com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.entities.FoodItemJpaEntity;
import com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.mappers.FoodItemPersistenceMapper;
import com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.repositories.SpringDataFoodItemJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FoodItemPersistenceAdapter implements FoodItemRepository {

    private final SpringDataFoodItemJpaRepository jpaRepository;

    public FoodItemPersistenceAdapter(SpringDataFoodItemJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<FoodItem> findById(Long id) {
        return jpaRepository.findById(id).map(FoodItemPersistenceMapper::toDomain);
    }

    @Override
    public List<FoodItem> findAll() {
        return jpaRepository.findAll().stream()
                .map(FoodItemPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<FoodItem> findByItemType(String itemType) {
        return jpaRepository.findByItemType(itemType).stream()
                .map(FoodItemPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<FoodItem> findByWeatherType(String weatherType) {
        return jpaRepository.findByWeatherTypesContaining(weatherType).stream()
                .map(FoodItemPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public FoodItem save(FoodItem foodItem) {
        return FoodItemPersistenceMapper.toDomain(
                jpaRepository.save(FoodItemPersistenceMapper.toJpaEntity(foodItem)));
    }

    @Override
    public List<FoodItem> saveAll(List<FoodItem> items) {
        var entities = items.stream()
                .map(FoodItemPersistenceMapper::toJpaEntity)
                .collect(Collectors.toList());
        return jpaRepository.saveAll(entities).stream()
                .map(FoodItemPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return jpaRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public boolean existsByNameKey(String nameKey) {
        return jpaRepository.existsByNameKey(nameKey);
    }

    @Override
    public Optional<FoodItem> findByNameKey(String nameKey) {
        return jpaRepository.findByNameKey(nameKey).stream()
                .findFirst()
                .map(FoodItemPersistenceMapper::toDomain);
    }

    @Override
    public List<FoodItem> findByNameContainingIgnoreCase(String name) {
        return jpaRepository.findByNameContainingIgnoreCase(name).stream()
                .map(FoodItemPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    @Override
    public List<FoodItem> findByRestrictions(List<String> restrictions) {
        if (restrictions.isEmpty()) return findAll();
        List<FoodItemJpaEntity> result = jpaRepository.findByRestrictionsContaining(restrictions.get(0));
        for (int i = 1; i < restrictions.size(); i++) {
            Set<Long> matchingIds = jpaRepository.findByRestrictionsContaining(restrictions.get(i))
                    .stream().map(FoodItemJpaEntity::getId).collect(Collectors.toSet());
            result = result.stream().filter(e -> matchingIds.contains(e.getId())).collect(Collectors.toList());
        }
        return result.stream().map(FoodItemPersistenceMapper::toDomain).collect(Collectors.toList());
    }
}
