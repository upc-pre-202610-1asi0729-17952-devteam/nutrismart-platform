package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.TravelContext;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.TravelContextResource;

public final class TravelContextResourceFromEntityAssembler {

    private TravelContextResourceFromEntityAssembler() {}

    public static TravelContextResource toResourceFromEntity(TravelContext context) {
        return new TravelContextResource(
                context.getId(),
                context.getUserId(),
                context.getCity(),
                context.getCountry(),
                context.getIsActive(),
                context.getIsManual(),
                context.getActivatedAt()
        );
    }
}
