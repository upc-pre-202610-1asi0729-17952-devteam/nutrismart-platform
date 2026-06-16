package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

import java.util.List;
import java.util.Map;

public interface PlateItemMatchPort {
    List<PlateItemMatchResult> matchOrEstimate(
            List<String> detectedNames,
            Map<String, List<FoodItemCandidate>> candidatesByName
    );
}
