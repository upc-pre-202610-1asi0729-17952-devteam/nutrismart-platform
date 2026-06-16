package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform;

import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.MealRecordCommandFailure;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.MealRecord;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.MealRecordResource;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromMealRecordCommandResultAssembler {

    private ResponseEntityFromMealRecordCommandResultAssembler() {}

    public static ResponseEntity<MealRecordResource> toResponseEntity(
            Result<MealRecord, MealRecordCommandFailure> result, HttpStatus successStatus) {
        return result.fold(
                domain -> ResponseEntity.status(successStatus)
                        .body(MealRecordResourceFromEntityAssembler.toResource(domain)),
                failure -> switch (failure) {
                    case MealRecordCommandFailure.NotFound ignored ->
                            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                    case MealRecordCommandFailure.InvalidData ignored ->
                            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                });
    }
}
