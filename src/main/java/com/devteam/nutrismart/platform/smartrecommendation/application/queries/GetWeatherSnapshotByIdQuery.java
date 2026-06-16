package com.devteam.nutrismart.platform.smartrecommendation.application.queries;

public record GetWeatherSnapshotByIdQuery(Long id) {

    public GetWeatherSnapshotByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("id must not be null and must be greater than 0");
    }
}
