package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.usda;

import com.devteam.nutrismart.platform.nutritiontracking.application.ports.ExternalFoodData;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.ExternalFoodDataPort;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class USDAFoodDataAdapter implements ExternalFoodDataPort {

    private static final String SOURCE = "USDA Foundation Foods";
    private static final double SERVING_SIZE = 100.0;
    private static final String SERVING_UNIT = "g";

    private static final Map<String, String> NUTRIENT_MAP = Map.ofEntries(
            Map.entry("Energy",                           "calories"),
            Map.entry("Energy (Atwater General Factors)", "calories"),
            Map.entry("Energy (Atwater Specific Factors)","calories"),
            Map.entry("Protein",                          "protein"),
            Map.entry("Carbohydrate, by difference",      "carbs"),
            Map.entry("Total lipid (fat)",                "fat"),
            Map.entry("Fiber, total dietary",             "fiber"),
            Map.entry("Sugars, total including NLEA",     "sugar"),
            Map.entry("Sugars, total",                    "sugar"),
            Map.entry("Total Sugars",                     "sugar")
    );

    private final USDAFoodDataClient client;

    public USDAFoodDataAdapter(USDAFoodDataClient client) {
        this.client = client;
    }

    @Override
    public List<ExternalFoodData> searchFoods(String query, int maxResults, String dataType) {
        USDAFoodSearchResponse response = client.searchFoods(query, maxResults, dataType);
        if (response == null || response.foods() == null) return Collections.emptyList();

        return response.foods().stream()
                .map(this::toExternalFoodData)
                .toList();
    }

    private ExternalFoodData toExternalFoodData(USDAFoodSearchResponse.USDAFoodItem item) {
        Map<String, Double> nutrients = extractNutrients(item.foodNutrients());
        return new ExternalFoodData(
                item.fdcId() != null ? item.fdcId().toString() : null,
                item.description(),
                nutrients.getOrDefault("calories", 0.0),
                nutrients.getOrDefault("protein", 0.0),
                nutrients.getOrDefault("carbs", 0.0),
                nutrients.getOrDefault("fat", 0.0),
                nutrients.getOrDefault("fiber", 0.0),
                nutrients.getOrDefault("sugar", 0.0),
                SERVING_SIZE,
                SERVING_UNIT,
                SOURCE
        );
    }

    private Map<String, Double> extractNutrients(List<USDAFoodSearchResponse.USDAFoodNutrient> nutrients) {
        if (nutrients == null) return Collections.emptyMap();
        Map<String, Double> result = new HashMap<>();
        for (USDAFoodSearchResponse.USDAFoodNutrient n : nutrients) {
            if (n.nutrientName() == null) continue;
            String internalKey = NUTRIENT_MAP.get(n.nutrientName());
            if (internalKey == null) continue;
            if ("calories".equals(internalKey) && !"kcal".equalsIgnoreCase(n.unitName())) continue;
            result.putIfAbsent(internalKey, n.value() != null ? n.value() : 0.0);
        }
        return result;
    }
}
