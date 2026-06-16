package com.devteam.nutrismart.platform.smartrecommendation.application.queries;

public record GetLocationSnapshotByIdQuery(Long id) {

    public GetLocationSnapshotByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("id must not be null and must be greater than 0");
    }
}
