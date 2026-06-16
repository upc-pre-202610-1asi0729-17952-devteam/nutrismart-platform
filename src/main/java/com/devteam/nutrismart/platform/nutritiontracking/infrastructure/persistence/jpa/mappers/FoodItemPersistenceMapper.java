package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.FoodRestriction;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.entities.FoodItemJpaEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FoodItemPersistenceMapper {

    private FoodItemPersistenceMapper() {}

    public static FoodItemJpaEntity toJpaEntity(FoodItem domain) {
        FoodItemJpaEntity entity = new FoodItemJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setNameEs(domain.getNameEs());
        entity.setSource(domain.getSource());
        entity.setServingSize(domain.getServingSize());
        entity.setServingUnit(domain.getServingUnit());
        entity.setCaloriesPer100g(domain.getCaloriesPer100g());
        entity.setProteinPer100g(domain.getProteinPer100g());
        entity.setCarbsPer100g(domain.getCarbsPer100g());
        entity.setFatPer100g(domain.getFatPer100g());
        entity.setFiberPer100g(domain.getFiberPer100g());
        entity.setSugarPer100g(domain.getSugarPer100g());
        entity.setRestrictions(toRestrictionsString(domain.getRestrictions()));
        entity.setNameKey(domain.getNameKey());
        entity.setCategory(domain.getCategory());
        entity.setItemType(domain.getItemType());
        entity.setWeatherTypes(toWeatherTypesString(domain.getWeatherTypes()));
        entity.setOriginCity(domain.getOriginCity());
        entity.setOriginCountry(domain.getOriginCountry());
        return entity;
    }

    public static FoodItem toDomain(FoodItemJpaEntity entity) {
        return FoodItem.rehydrate(
                entity.getId(), entity.getName(), entity.getNameEs(), entity.getSource(),
                entity.getServingSize(), entity.getServingUnit(), entity.getCaloriesPer100g(),
                entity.getProteinPer100g(), entity.getCarbsPer100g(), entity.getFatPer100g(),
                entity.getFiberPer100g(), entity.getSugarPer100g(),
                toRestrictionsList(entity.getRestrictions()),
                entity.getNameKey(), entity.getCategory(), entity.getItemType(),
                toWeatherTypesList(entity.getWeatherTypes()),
                entity.getOriginCity(), entity.getOriginCountry());
    }

    private static String toRestrictionsString(List<FoodRestriction> restrictions) {
        if (restrictions == null || restrictions.isEmpty()) return "";
        return restrictions.stream().map(Enum::name).collect(Collectors.joining(","));
    }

    private static List<FoodRestriction> toRestrictionsList(String csv) {
        if (csv == null || csv.isBlank()) return Collections.emptyList();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(FoodRestriction::valueOf)
                .collect(Collectors.toList());
    }

    private static String toWeatherTypesString(List<String> weatherTypes) {
        if (weatherTypes == null || weatherTypes.isEmpty()) return "";
        return String.join(",", weatherTypes);
    }

    private static List<String> toWeatherTypesList(String csv) {
        if (csv == null || csv.isBlank()) return Collections.emptyList();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
