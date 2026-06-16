package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources;

import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateBillingRecordResource(
        @NotNull Long userId,
        @NotNull SubscriptionPlan plan,
        @NotNull @PositiveOrZero Double amount,
        @NotBlank String currency,
        @NotBlank String status
) {}
