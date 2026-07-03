package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.mappers;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.WearableConnection;
import com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities.WearableConnectionJpaEntity;

public final class WearableConnectionPersistenceMapper {

    private WearableConnectionPersistenceMapper() {}

    public static WearableConnectionJpaEntity toJpaEntity(WearableConnection domain) {
        WearableConnectionJpaEntity entity = new WearableConnectionJpaEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setProvider(domain.getProvider());
        entity.setStatus(domain.getStatus());
        entity.setLastSyncedAt(domain.getLastSyncedAt());
        entity.setAuthorizedAt(domain.getAuthorizedAt());
        entity.setAutoSyncEnabled(domain.isAutoSyncEnabled());
        entity.setAccessToken(domain.getAccessToken());
        entity.setRefreshToken(domain.getRefreshToken());
        entity.setTokenExpiresAt(domain.getTokenExpiresAt());
        return entity;
    }

    public static WearableConnection toDomain(WearableConnectionJpaEntity entity) {
        return WearableConnection.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getProvider(),
                entity.getStatus(),
                entity.getLastSyncedAt(),
                entity.getAuthorizedAt(),
                entity.isAutoSyncEnabled(),
                entity.getAccessToken(),
                entity.getRefreshToken(),
                entity.getTokenExpiresAt()
        );
    }
}
