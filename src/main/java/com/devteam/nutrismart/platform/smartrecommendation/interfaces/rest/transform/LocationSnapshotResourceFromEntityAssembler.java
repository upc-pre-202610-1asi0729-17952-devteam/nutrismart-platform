package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.LocationSnapshot;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.LocationSnapshotResource;

public final class LocationSnapshotResourceFromEntityAssembler {

    private LocationSnapshotResourceFromEntityAssembler() {}

    public static LocationSnapshotResource toResourceFromEntity(LocationSnapshot snapshot) {
        return new LocationSnapshotResource(
                snapshot.getId(),
                snapshot.getUserId(),
                snapshot.getCity(),
                snapshot.getCountry(),
                snapshot.getRecordedAt()
        );
    }
}
