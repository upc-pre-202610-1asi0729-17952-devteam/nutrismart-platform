package com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform;

import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.BillingRecord;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.BillingRecordResource;

public final class BillingRecordResourceFromEntityAssembler {

    private BillingRecordResourceFromEntityAssembler() {}

    public static BillingRecordResource toResourceFromEntity(BillingRecord billingRecord) {
        return new BillingRecordResource(
                billingRecord.getId(),
                billingRecord.getUserId(),
                billingRecord.getPlan(),
                billingRecord.getAmount(),
                billingRecord.getCurrency(),
                billingRecord.getPaidAt(),
                billingRecord.getStatus()
        );
    }
}
