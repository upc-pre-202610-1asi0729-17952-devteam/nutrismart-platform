package com.devteam.nutrismart.platform.smartrecommendation.application.ports;

import java.util.List;
import java.util.Map;

public interface IngredientLookupPort {

    Map<String, List<String>> findIngredientNameKeysByCategory(List<String> categories);

    boolean existsByNameKey(String nameKey);
}
