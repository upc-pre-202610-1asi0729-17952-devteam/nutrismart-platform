package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform;

import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.subscriptions.application.commandservices.SubscriptionCommandFailure;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.Subscription;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromSubscriptionCommandResultAssembler {

    private ResponseEntityFromSubscriptionCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<Subscription, SubscriptionCommandFailure> result) {
        return result.fold(
                subscription -> ResponseEntity.status(201)
                        .body(SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription)),
                failure -> switch (failure) {
                    case SubscriptionCommandFailure.AlreadyActive f ->
                            ResponseEntity.status(409).body(Map.of("message",
                                    "User already has an active subscription: " + f.userId()));
                    case SubscriptionCommandFailure.NotFound f ->
                            ResponseEntity.status(404).body(Map.of("message",
                                    "Subscription not found: " + f.id()));
                    case SubscriptionCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }

    public static ResponseEntity<?> toResponseEntityFromUpdateResult(Result<Subscription, SubscriptionCommandFailure> result) {
        return result.fold(
                subscription -> ResponseEntity.ok(
                        SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription)),
                failure -> switch (failure) {
                    case SubscriptionCommandFailure.NotFound f ->
                            ResponseEntity.status(404).body(Map.of("message",
                                    "Subscription not found: " + f.id()));
                    case SubscriptionCommandFailure.AlreadyActive f ->
                            ResponseEntity.status(409).body(Map.of("message",
                                    "User already has an active subscription: " + f.userId()));
                    case SubscriptionCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
