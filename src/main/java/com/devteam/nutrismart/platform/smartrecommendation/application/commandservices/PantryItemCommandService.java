package com.devteam.nutrismart.platform.smartrecommendation.application.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commands.AddPantryItemCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.DeletePantryItemCommand;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.PantryItem;
import com.devteam.nutrismart.platform.shared.application.result.Result;

public interface PantryItemCommandService {

    Result<PantryItem, PantryItemCommandFailure> handle(AddPantryItemCommand command);

    Result<Void, PantryItemCommandFailure> handle(DeletePantryItemCommand command);
}
