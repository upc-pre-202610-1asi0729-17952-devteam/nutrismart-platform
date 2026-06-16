package com.devteam.nutrismart.platform.nutritiontracking.application.internal.queryservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetAllFoodItemsQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queryservices.FoodItemQueryService;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemQueryServiceImpl implements FoodItemQueryService {

    private final FoodItemRepository foodItemRepository;

    public FoodItemQueryServiceImpl(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public List<FoodItem> handle(GetAllFoodItemsQuery query) {
        if (query.restrictions() == null || query.restrictions().isEmpty()) {
            return foodItemRepository.findAll();
        }
        return foodItemRepository.findByRestrictions(query.restrictions());
    }
}
