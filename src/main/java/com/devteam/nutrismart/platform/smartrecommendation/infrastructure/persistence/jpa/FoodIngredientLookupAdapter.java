package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.FoodItemRepository;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.IngredientLookupPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FoodIngredientLookupAdapter implements IngredientLookupPort {

    private final FoodItemRepository foodItemRepository;

    public FoodIngredientLookupAdapter(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public Map<String, List<String>> findIngredientNameKeysByCategory(List<String> categories) {
        var ingredients = foodItemRepository.findByItemType("INGREDIENT");

        return ingredients.stream()
                .filter(item -> item.getNameKey() != null && !item.getNameKey().isBlank())
                .filter(item -> categories == null || categories.isEmpty()
                        || categories.contains(item.getCategory()))
                .collect(Collectors.groupingBy(
                        item -> item.getCategory() != null ? item.getCategory() : "Other",
                        Collectors.mapping(item -> item.getNameKey(), Collectors.toList())
                ));
    }

    @Override
    public boolean existsByNameKey(String nameKey) {
        return foodItemRepository.existsByNameKey(nameKey);
    }
}
