package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.RecipeSuggestionCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.RecipeSuggestion;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromRecipeSuggestionCommandResultAssembler {

    private ResponseEntityFromRecipeSuggestionCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<RecipeSuggestion, RecipeSuggestionCommandFailure> result) {
        return result.fold(
                recipe -> ResponseEntity.status(201).body(RecipeSuggestionResourceFromEntityAssembler.toResourceFromEntity(recipe)),
                failure -> switch (failure) {
                    case RecipeSuggestionCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
