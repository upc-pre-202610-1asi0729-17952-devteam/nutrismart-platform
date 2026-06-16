package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateTravelContextCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.UpdateTravelContextCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.TravelContext;
import com.devteam.nutrismart.platform.shared.application.result.Result;

public interface TravelContextCommandService {

    Result<TravelContext, TravelContextCommandFailure> handle(CreateTravelContextCommand command);

    Result<TravelContext, TravelContextCommandFailure> handle(UpdateTravelContextCommand command);
}
