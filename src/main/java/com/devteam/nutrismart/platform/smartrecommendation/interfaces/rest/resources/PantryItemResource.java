package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

public record PantryItemResource(
        Long id,
        Long userId,
        Long foodId,
        Double quantityG
) {}
