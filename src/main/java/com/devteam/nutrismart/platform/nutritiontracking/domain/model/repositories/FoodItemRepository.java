package com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;

import java.util.List;
import java.util.Optional;

public interface FoodItemRepository {
    Optional<FoodItem> findById(Long id);
    List<FoodItem> findAll();
    List<FoodItem> findByItemType(String itemType);
    List<FoodItem> findByWeatherType(String weatherType);
    FoodItem save(FoodItem foodItem);
    List<FoodItem> saveAll(List<FoodItem> items);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameKey(String nameKey);
    Optional<FoodItem> findByNameKey(String nameKey);
    List<FoodItem> findByNameContainingIgnoreCase(String name);
    List<FoodItem> findByRestrictions(List<String> restrictions);
    long count();
}
