package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.PantryItem;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.PantryItemResource;

public final class PantryItemResourceFromEntityAssembler {

    private PantryItemResourceFromEntityAssembler() {}

    public static PantryItemResource toResourceFromEntity(PantryItem item) {
        return new PantryItemResource(
                item.getId(),
                item.getUserId(),
                item.getFoodId(),
                item.getQuantityG()
        );
    }
}
