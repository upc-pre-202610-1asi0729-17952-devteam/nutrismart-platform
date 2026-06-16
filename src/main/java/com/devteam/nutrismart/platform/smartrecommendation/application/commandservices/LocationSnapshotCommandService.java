package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateLocationSnapshotCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.LocationSnapshot;
import com.devteam.nutrismart.platform.shared.application.result.Result;

public interface LocationSnapshotCommandService {

    Result<LocationSnapshot, LocationSnapshotCommandFailure> handle(CreateLocationSnapshotCommand command);
}
