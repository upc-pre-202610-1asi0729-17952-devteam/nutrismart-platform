package com.devteam.nutrismart.platform.smartrecommendation.application.queryservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllPantryItemsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetPantryItemByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.PantryItem;

import java.util.List;
import java.util.Optional;

public interface PantryItemQueryService {

    Optional<PantryItem> handle(GetPantryItemByIdQuery query);

    List<PantryItem> handle(GetAllPantryItemsQuery query);
}
