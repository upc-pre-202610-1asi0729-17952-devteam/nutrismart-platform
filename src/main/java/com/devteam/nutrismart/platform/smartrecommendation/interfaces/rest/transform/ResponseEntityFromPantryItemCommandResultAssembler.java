package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.PantryItemCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.PantryItem;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromPantryItemCommandResultAssembler {

    private ResponseEntityFromPantryItemCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromAddResult(Result<PantryItem, PantryItemCommandFailure> result) {
        return result.fold(
                item -> ResponseEntity.status(201).body(PantryItemResourceFromEntityAssembler.toResourceFromEntity(item)),
                failure -> switch (failure) {
                    case PantryItemCommandFailure.PantryItemNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Pantry item not found: " + f.id()));
                    case PantryItemCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }

    public static ResponseEntity<?> toResponseEntityFromDeleteResult(Result<Void, PantryItemCommandFailure> result) {
        return result.fold(
                v -> ResponseEntity.noContent().build(),
                failure -> switch (failure) {
                    case PantryItemCommandFailure.PantryItemNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "Pantry item not found: " + f.id()));
                    case PantryItemCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
