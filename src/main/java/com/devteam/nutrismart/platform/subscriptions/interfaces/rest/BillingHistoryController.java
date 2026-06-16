package com.devteam.nutrismart.platform.subscriptions.interfaces.rest;

import com.devteam.nutrismart.platform.subscriptions.application.commandservices.BillingRecordCommandService;
import com.devteam.nutrismart.platform.subscriptions.application.queries.GetAllBillingRecordsQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queries.GetBillingRecordByIdQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queryservices.BillingRecordQueryService;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.BillingRecordResource;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.CreateBillingRecordResource;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform.BillingRecordCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform.BillingRecordResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform.ResponseEntityFromBillingRecordCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que expone los endpoints del historial de facturación.
 * Permite consultar y registrar pagos de suscripciones de los usuarios.
 */
@RestController
@RequestMapping("/api/v1/billing-history")
@Tag(name = "Billing History", description = "Billing history management endpoints")
public class BillingHistoryController {

    private final BillingRecordCommandService commandService;
    private final BillingRecordQueryService queryService;

    public BillingHistoryController(BillingRecordCommandService commandService,
                                    BillingRecordQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all billing records",
               description = "Retrieves all billing records. Optionally filter by userId to get records for a specific user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Billing records retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<BillingRecordResource>> getAllBillingRecords(
            @Parameter(description = "Filter billing records by user ID (optional)", example = "1")
            @RequestParam(required = false) Long userId) {
        var records = (userId != null)
                ? queryService.findByUserId(userId)
                : queryService.handle(new GetAllBillingRecordsQuery());
        return ResponseEntity.ok(records.stream()
                .map(BillingRecordResourceFromEntityAssembler::toResourceFromEntity)
                .toList());
    }

    @Operation(summary = "Get billing record by ID",
               description = "Retrieves a single billing record by its unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Billing record retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Billing record not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BillingRecordResource> getBillingRecordById(
            @Parameter(description = "Unique identifier of the billing record", example = "1")
            @PathVariable Long id) {
        return queryService.handle(new GetBillingRecordByIdQuery(id))
                .map(BillingRecordResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new billing record",
               description = "Registers a new payment in the billing history for a user.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Billing record created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<?> createBillingRecord(@Valid @RequestBody CreateBillingRecordResource resource) {
        var command = BillingRecordCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromBillingRecordCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }
}
