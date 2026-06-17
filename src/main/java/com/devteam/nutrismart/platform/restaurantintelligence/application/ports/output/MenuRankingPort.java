package com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output;

import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.FoodItemCandidate;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.MenuDishCandidate;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.RankedDishData;
import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.UserProfileData;

import java.util.List;

public interface MenuRankingPort {
    List<RankedDishData> rankMenuDishes(
            List<MenuDishCandidate> dishes,
            List<FoodItemCandidate> existingMatches,
            UserProfileData profile
    );
}
