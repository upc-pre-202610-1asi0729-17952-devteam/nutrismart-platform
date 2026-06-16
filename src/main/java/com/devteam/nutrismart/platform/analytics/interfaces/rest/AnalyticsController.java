package com.devteam.nutrismart.platform.analytics.interfaces.rest;

import com.devteam.nutrismart.platform.analytics.application.commandservices.AnalyticsCommandFailure;
import com.devteam.nutrismart.platform.analytics.application.commandservices.AnalyticsCommandService;
import com.devteam.nutrismart.platform.analytics.application.commands.UpdateDailyDashboardCommand;
import com.devteam.nutrismart.platform.analytics.application.queries.GetDashboardByUserIdQuery;
import com.devteam.nutrismart.platform.analytics.application.queryservices.AnalyticsQueryService;
import com.devteam.nutrismart.platform.analytics.interfaces.rest.resources.AnalyticsResource;
import com.devteam.nutrismart.platform.analytics.interfaces.rest.resources.UpdateDashboardResource;
import com.devteam.nutrismart.platform.analytics.interfaces.rest.transform.AnalyticsResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST que expone los endpoints del módulo de analíticas.
 * Permite consultar el panel de control diario de un usuario y solicitar
 * su actualización mediante comandos de escritura.
 */
@RestController
@RequestMapping("/api/v1/analytics")
@Tag(name = "Analytics", description = "Endpoints for retrieving and updating user analytics dashboards")
public class AnalyticsController {

    private final AnalyticsQueryService queryService;
    private final AnalyticsCommandService commandService;

    public AnalyticsController(AnalyticsQueryService queryService, AnalyticsCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @Operation(summary = "Get analytics dashboard by user ID",
               description = "Retrieves the consolidated daily analytics dashboard for the specified user, " +
                             "including calorie targets, macro consumption, adherence status, and body metrics.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dashboard retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<AnalyticsResource> getDashboard(
            @Parameter(description = "Unique identifier of the user", example = "1")
            @PathVariable Long userId) {
        return queryService.handle(new GetDashboardByUserIdQuery(userId))
                .map(AnalyticsResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update daily analytics dashboard",
               description = "Triggers a recalculation of the user's daily analytics dashboard for the specified date. " +
                             "Returns the updated dashboard or an error if the user does not exist.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dashboard updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/dashboard/update")
    public ResponseEntity<?> updateDashboard(@RequestBody UpdateDashboardResource resource) {
        var command = new UpdateDailyDashboardCommand(resource.userId(), resource.date());
        return commandService.handle(command).fold(
                analytics -> ResponseEntity.ok(AnalyticsResourceFromEntityAssembler.toResourceFromEntity(analytics)),
                failure -> switch (failure) {
                    case AnalyticsCommandFailure.UserNotFound u ->
                            ResponseEntity.notFound().build();
                    case AnalyticsCommandFailure.InvalidData d ->
                            ResponseEntity.badRequest().body(d.reason());
                });
    }
}
