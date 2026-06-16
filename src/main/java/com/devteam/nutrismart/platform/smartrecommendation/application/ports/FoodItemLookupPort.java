package com.devteam.nutrismart.platform.smartrecommendation.application.ports;

import java.util.List;

public interface FoodItemLookupPort {
    List<FoodItemData> findByCriteria(List<String> categories, List<String> restrictionNames, String weatherType, String travelCountry);
}
