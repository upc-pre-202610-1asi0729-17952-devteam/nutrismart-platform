package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

public sealed interface PantryItemCommandFailure
        permits PantryItemCommandFailure.PantryItemNotFound,
                PantryItemCommandFailure.InvalidData,
                PantryItemCommandFailure.DuplicateItem {

    record PantryItemNotFound(Long id) implements PantryItemCommandFailure {}

    record InvalidData(String reason) implements PantryItemCommandFailure {}

    record DuplicateItem(Long userId, Long foodId) implements PantryItemCommandFailure {}
}
