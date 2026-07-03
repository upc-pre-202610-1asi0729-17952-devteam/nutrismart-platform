package com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest;

import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetDailyIntakeByUserIdAndDateQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queries.GetDailyIntakeByUserIdQuery;
import com.devteam.nutrismart.platform.nutritiontracking.application.queryservices.DailyIntakeQueryService;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.resources.DailyIntakeResource;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.rest.transform.DailyIntakeResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.shared.infrastructure.security.CurrentUserGuard;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para consultar el balance nutricional diario de los usuarios.
 * Expone endpoints de solo lectura que permiten obtener los registros de ingesta diaria
 * filtrados por usuario y/o fecha.
 */
@Tag(name = "Daily Balance", description = "Daily nutritional balance endpoints")
@RestController
@RequestMapping("/api/v1/daily-balance")
public class DailyBalanceController {

    private final DailyIntakeQueryService queryService;
    private final CurrentUserGuard currentUser;

    public DailyBalanceController(DailyIntakeQueryService queryService, CurrentUserGuard currentUser) {
        this.queryService = queryService;
        this.currentUser = currentUser;
    }

    @Operation(
        summary = "Get daily balance records",
        description = "Retrieves daily nutritional balance records. Can be filtered by userId and/or date. " +
                      "If both are provided, returns the single record for that user on that date. " +
                      "If only userId is provided, returns all records for that user. " +
                      "If neither is provided, returns all records in the system."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Daily balance records retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid date format provided"),
        @ApiResponse(responseCode = "401", description = "Authentication required"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<DailyIntakeResource>> getAll(
            @Parameter(description = "ID of the user whose balance records are requested", example = "1")
            @RequestParam(required = false) Long userId,
            @Parameter(description = "Date to filter records in ISO-8601 format (yyyy-MM-dd)", example = "2024-06-15")
            @RequestParam(required = false) String date) {
        Long scopedUserId = currentUser.scopeToOwner(userId);
        List<DailyIntakeResource> resources;
        if (date != null) {
            LocalDate localDate = LocalDate.parse(date);
            resources = queryService.handle(new GetDailyIntakeByUserIdAndDateQuery(scopedUserId, localDate))
                    .map(DailyIntakeResourceFromEntityAssembler::toResource)
                    .map(List::of)
                    .orElse(List.of());
        } else {
            resources = queryService.handle(new GetDailyIntakeByUserIdQuery(scopedUserId)).stream()
                    .map(DailyIntakeResourceFromEntityAssembler::toResource)
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(resources);
    }

}
