package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform;

import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.subscriptions.application.commandservices.BillingRecordCommandFailure;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.BillingRecord;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public final class ResponseEntityFromBillingRecordCommandResultAssembler {

    private ResponseEntityFromBillingRecordCommandResultAssembler() {}

    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<BillingRecord, BillingRecordCommandFailure> result) {
        return result.fold(
                billingRecord -> ResponseEntity.status(201)
                        .body(BillingRecordResourceFromEntityAssembler.toResourceFromEntity(billingRecord)),
                failure -> switch (failure) {
                    case BillingRecordCommandFailure.NotFound f ->
                            ResponseEntity.status(404).body(Map.of("message",
                                    "Billing record not found: " + f.id()));
                    case BillingRecordCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
