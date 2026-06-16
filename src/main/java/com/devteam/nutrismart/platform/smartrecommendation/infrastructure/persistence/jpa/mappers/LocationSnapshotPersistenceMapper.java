package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.LocationSnapshot;
import com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities.LocationSnapshotJpaEntity;

public final class LocationSnapshotPersistenceMapper {

    private LocationSnapshotPersistenceMapper() {}

    public static LocationSnapshotJpaEntity toJpaEntity(LocationSnapshot snapshot) {
        LocationSnapshotJpaEntity entity = new LocationSnapshotJpaEntity();
        entity.setId(snapshot.getId());
        entity.setUserId(snapshot.getUserId());
        entity.setCity(snapshot.getCity());
        entity.setCountry(snapshot.getCountry());
        entity.setRecordedAt(snapshot.getRecordedAt());
        return entity;
    }

    public static LocationSnapshot toDomain(LocationSnapshotJpaEntity entity) {
        return LocationSnapshot.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getCity(),
                entity.getCountry(),
                entity.getRecordedAt()
        );
    }
}
