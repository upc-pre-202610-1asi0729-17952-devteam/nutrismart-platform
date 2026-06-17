package com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output;

import com.devteam.nutrismart.platform.restaurantintelligence.application.ports.output.dto.MenuDishCandidate;

import java.util.List;

public interface MenuImageRecognitionPort {
    List<MenuDishCandidate> extractMenuItems(String imageBase64);
}
