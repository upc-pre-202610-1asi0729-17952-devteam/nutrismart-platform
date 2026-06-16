package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

public sealed interface RecipeSuggestionCommandFailure
        permits RecipeSuggestionCommandFailure.InvalidData {

    record InvalidData(String reason) implements RecipeSuggestionCommandFailure {}
}
