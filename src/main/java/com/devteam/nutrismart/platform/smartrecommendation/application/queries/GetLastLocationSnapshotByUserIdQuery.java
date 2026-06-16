package com.devteam.nutrismart.platform.smartrecommendation.application.queries;

public record GetLastLocationSnapshotByUserIdQuery(Long userId) {

    public GetLastLocationSnapshotByUserIdQuery {
        if (userId == null || userId <= 0) throw new IllegalArgumentException("userId must not be null and must be greater than 0");
    }
}
