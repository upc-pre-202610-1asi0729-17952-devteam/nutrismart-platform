package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateSubscriptionResource(
        @NotNull SubscriptionPlan plan,
        @NotNull SubscriptionStatus status,
        @NotNull LocalDate billingCycleStart,
        @NotNull LocalDate billingCycleEnd
) {}
