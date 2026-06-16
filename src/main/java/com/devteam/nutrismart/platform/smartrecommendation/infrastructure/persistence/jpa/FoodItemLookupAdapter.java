package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.repositories.FoodItemRepository;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.FoodItemData;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.FoodItemLookupPort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
public class FoodItemLookupAdapter implements FoodItemLookupPort {

    private static final Map<String, String> ISO_TO_COUNTRY = Map.ofEntries(
            Map.entry("PE", "Peru"),          Map.entry("CL", "Chile"),
            Map.entry("AR", "Argentina"),     Map.entry("MX", "Mexico"),
            Map.entry("BR", "Brazil"),        Map.entry("US", "United States"),
            Map.entry("FR", "France"),        Map.entry("IT", "Italy"),
            Map.entry("ES", "Spain"),         Map.entry("JP", "Japan"),
            Map.entry("CN", "China"),         Map.entry("DE", "Germany"),
            Map.entry("KR", "South Korea"),   Map.entry("IN", "India"),
            Map.entry("TH", "Thailand"),      Map.entry("GR", "Greece"),
            Map.entry("TR", "Turkey"),        Map.entry("RU", "Russia"),
            Map.entry("UA", "Ukraine"),       Map.entry("NO", "Norway"),
            Map.entry("SE", "Sweden"),        Map.entry("CH", "Switzerland"),
            Map.entry("BE", "Belgium"),       Map.entry("IL", "Israel"),
            Map.entry("EG", "Egypt"),         Map.entry("CU", "Cuba"),
            Map.entry("PH", "Philippines"),   Map.entry("GY", "Guyana"),
            Map.entry("SV", "El Salvador"),   Map.entry("PR", "Puerto Rico"),
            Map.entry("GB", "United Kingdom")
    );

    private final FoodItemRepository foodItemRepository;

    public FoodItemLookupAdapter(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public List<FoodItemData> findByCriteria(List<String> categories, List<String> restrictionNames,
                                              String weatherType, String travelCountry) {
        List<FoodItem> base = (weatherType != null && !weatherType.isBlank())
                ? foodItemRepository.findByWeatherType(weatherType)
                : foodItemRepository.findAll();

        List<FoodItem> shuffled = new ArrayList<>(base);
        Collections.shuffle(shuffled);

        List<FoodItem> filtered = new ArrayList<>(shuffled.stream()
                .filter(item -> categories == null || categories.isEmpty()
                        || categories.contains(item.getCategory()))
                .filter(item -> {
                    if (restrictionNames == null || restrictionNames.isEmpty()) return true;
                    if (item.getRestrictions() == null || item.getRestrictions().isEmpty()) return false;
                    List<String> itemRestrictionNames = item.getRestrictions().stream()
                            .map(Enum::name)
                            .toList();
                    return itemRestrictionNames.containsAll(restrictionNames);
                })
                .toList());

        if (travelCountry != null && !travelCountry.isBlank()) {
            String fullCountry = ISO_TO_COUNTRY.getOrDefault(travelCountry.toUpperCase(), travelCountry);
            filtered.sort(Comparator.comparingInt((FoodItem item) -> {
                boolean isDish       = "Dish".equalsIgnoreCase(item.getCategory());
                boolean matchCountry = fullCountry.equalsIgnoreCase(item.getOriginCountry());
                if (isDish && matchCountry) return 0;
                if (isDish)                return 1;
                if (matchCountry)          return 2;
                return 3;
            }));
        }

        return filtered.stream().map(this::toData).toList();
    }

    private FoodItemData toData(FoodItem item) {
        List<String> restrictionNames = item.getRestrictions() != null
                ? item.getRestrictions().stream().map(Enum::name).toList()
                : List.of();
        return new FoodItemData(
                item.getId(),
                item.getName(),
                item.getNameEs(),
                item.getCategory(),
                restrictionNames,
                item.getOriginCity(),
                item.getOriginCountry(),
                item.getCaloriesPer100g(),
                item.getProteinPer100g()
        );
    }
}
