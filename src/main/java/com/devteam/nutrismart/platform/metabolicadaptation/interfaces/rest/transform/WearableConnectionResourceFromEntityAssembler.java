package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.WearableConnection;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.WearableConnectionResource;

public final class WearableConnectionResourceFromEntityAssembler {

    private WearableConnectionResourceFromEntityAssembler() {}

    public static WearableConnectionResource toResourceFromEntity(WearableConnection entity) {
        return new WearableConnectionResource(
                entity.getId(),
                entity.getUserId(),
                entity.getProvider(),
                entity.getStatus(),
                entity.getLastSyncedAt(),
                entity.getAuthorizedAt()
        );
    }
}
