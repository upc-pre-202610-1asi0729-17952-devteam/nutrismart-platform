package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform;

import com.devteam.nutrismart.platform.nutritiontracking.application.commandservices.FoodItemCommandFailure;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates.FoodItem;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.FoodItemResource;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromFoodItemCommandResultAssembler {

    private ResponseEntityFromFoodItemCommandResultAssembler() {}

    public static ResponseEntity<FoodItemResource> toResponseEntity(
            Result<FoodItem, FoodItemCommandFailure> result, HttpStatus successStatus) {
        return result.fold(
                domain -> ResponseEntity.status(successStatus)
                        .body(FoodItemResourceFromEntityAssembler.toResource(domain)),
                failure -> switch (failure) {
                    case FoodItemCommandFailure.NotFound ignored ->
                            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                    case FoodItemCommandFailure.InvalidData ignored ->
                            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                });
    }
}
