package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

public sealed interface TravelContextCommandFailure
        permits TravelContextCommandFailure.TravelContextNotFound,
                TravelContextCommandFailure.InvalidData {

    record TravelContextNotFound(Long id) implements TravelContextCommandFailure {}

    record InvalidData(String reason) implements TravelContextCommandFailure {}
}
