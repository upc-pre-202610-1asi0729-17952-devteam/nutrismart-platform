package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;

import java.time.Instant;

public record WeatherSnapshotResource(
        Long id,
        String city,
        String country,
        Double temperatureCelsius,
        String condition,
        WeatherType weatherType,
        Instant updatedAt
) {}
