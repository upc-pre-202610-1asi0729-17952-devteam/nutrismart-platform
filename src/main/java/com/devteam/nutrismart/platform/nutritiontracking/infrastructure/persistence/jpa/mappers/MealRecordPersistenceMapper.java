package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.MealRecord;
import com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.entities.MealRecordJpaEntity;

public class MealRecordPersistenceMapper {

    private MealRecordPersistenceMapper() {}

    public static MealRecordJpaEntity toJpaEntity(MealRecord domain) {
        MealRecordJpaEntity entity = new MealRecordJpaEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setFoodId(domain.getFoodId());
        entity.setFoodItemName(domain.getFoodItemName());
        entity.setFoodItemNameEs(domain.getFoodItemNameEs());
        entity.setMealType(domain.getMealType());
        entity.setQuantity(domain.getQuantity());
        entity.setUnit(domain.getUnit());
        entity.setCalories(domain.getCalories());
        entity.setProtein(domain.getProtein());
        entity.setCarbs(domain.getCarbs());
        entity.setFat(domain.getFat());
        entity.setFiber(domain.getFiber());
        entity.setSugar(domain.getSugar());
        entity.setLoggedAt(domain.getLoggedAt());
        return entity;
    }

    public static MealRecord toDomain(MealRecordJpaEntity entity) {
        return MealRecord.rehydrate(
                entity.getId(), entity.getUserId(), entity.getFoodId(),
                entity.getFoodItemName(), entity.getFoodItemNameEs(),
                entity.getMealType(), entity.getQuantity(), entity.getUnit(),
                entity.getCalories(), entity.getProtein(), entity.getCarbs(),
                entity.getFat(), entity.getFiber(), entity.getSugar(), entity.getLoggedAt());
    }
}
