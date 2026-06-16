package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.AddPantryItemCommand;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreatePantryItemResource;

public final class AddPantryItemCommandFromResourceAssembler {

    private AddPantryItemCommandFromResourceAssembler() {}

    public static AddPantryItemCommand toCommandFromResource(CreatePantryItemResource resource) {
        return new AddPantryItemCommand(resource.userId(), resource.foodId(), resource.quantityG());
    }
}
