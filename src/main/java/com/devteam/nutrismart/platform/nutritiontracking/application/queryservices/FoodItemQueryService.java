package com.devteam.nutrismart.platform.nutritiontracking.application.queryservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetAllFoodItemsQuery;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;

import java.util.List;

public interface FoodItemQueryService {
    List<FoodItem> handle(GetAllFoodItemsQuery query);
}
