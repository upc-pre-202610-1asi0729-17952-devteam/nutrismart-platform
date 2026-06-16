package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.RecoveryPlan;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.RecoveryPlanResource;

public final class RecoveryPlanResourceFromEntityAssembler {

    private RecoveryPlanResourceFromEntityAssembler() {}

    public static RecoveryPlanResource toResourceFromEntity(RecoveryPlan domain) {
        return new RecoveryPlanResource(
                domain.getId(),
                domain.getUserId(),
                domain.getStatus(),
                domain.getTrigger(),
                domain.getActions(),
                domain.getCreatedAt(),
                domain.getExpiresAt());
    }
}
