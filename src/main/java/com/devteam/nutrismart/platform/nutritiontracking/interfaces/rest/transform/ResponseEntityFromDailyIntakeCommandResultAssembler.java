package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform;

import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.DailyIntakeCommandFailure;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.DailyIntake;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.DailyIntakeResource;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromDailyIntakeCommandResultAssembler {

    private ResponseEntityFromDailyIntakeCommandResultAssembler() {}

    public static ResponseEntity<DailyIntakeResource> toResponseEntity(
            Result<DailyIntake, DailyIntakeCommandFailure> result, HttpStatus successStatus) {
        return result.fold(
                domain -> ResponseEntity.status(successStatus)
                        .body(DailyIntakeResourceFromEntityAssembler.toResource(domain)),
                failure -> switch (failure) {
                    case DailyIntakeCommandFailure.NotFound ignored ->
                            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                    case DailyIntakeCommandFailure.DuplicateEntry ignored ->
                            ResponseEntity.status(HttpStatus.CONFLICT).build();
                    case DailyIntakeCommandFailure.InvalidData ignored ->
                            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                });
    }
}
