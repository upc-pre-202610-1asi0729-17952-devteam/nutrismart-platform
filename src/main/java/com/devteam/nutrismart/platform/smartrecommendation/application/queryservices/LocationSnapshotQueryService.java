package com.devteam.nutrismart.platform.smartrecommendation.application.queryservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllLocationSnapshotsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetLastLocationSnapshotByUserIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetLocationSnapshotByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.LocationSnapshot;

import java.util.List;
import java.util.Optional;

public interface LocationSnapshotQueryService {

    Optional<LocationSnapshot> handle(GetLocationSnapshotByIdQuery query);

    List<LocationSnapshot> handle(GetAllLocationSnapshotsQuery query);

    Optional<LocationSnapshot> handle(GetLastLocationSnapshotByUserIdQuery query);
}
