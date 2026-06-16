package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import jakarta.validation.constraints.NotNull;

public record CreateSubscriptionResource(
        @NotNull Long userId,
        @NotNull SubscriptionPlan plan
) {}
