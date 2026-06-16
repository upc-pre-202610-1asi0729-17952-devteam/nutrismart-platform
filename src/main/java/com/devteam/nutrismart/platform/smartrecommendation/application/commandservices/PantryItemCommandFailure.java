package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

public sealed interface PantryItemCommandFailure
        permits PantryItemCommandFailure.PantryItemNotFound,
                PantryItemCommandFailure.InvalidData {

    record PantryItemNotFound(Long id) implements PantryItemCommandFailure {}

    record InvalidData(String reason) implements PantryItemCommandFailure {}
}
