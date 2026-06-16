package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.BodyMetricCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllBodyMetricsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.BodyMetricQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.BodyMetricResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.CreateBodyMetricResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.UpdateBodyMetricResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.BodyMetricResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.LogBodyMetricsCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.ResponseEntityFromBodyMetricCommandResultAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.UpdateBodyMetricCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/body-metrics")
@Tag(name = "Body Metrics", description = "Body metrics management endpoints")
public class BodyMetricsController {

    private final BodyMetricCommandService commandService;
    private final BodyMetricQueryService queryService;

    public BodyMetricsController(BodyMetricCommandService commandService, BodyMetricQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<BodyMetricResource>> getAllBodyMetrics(
            @RequestParam(required = false) Long userId) {
        var metrics = queryService.handle(new GetAllBodyMetricsQuery(userId)).stream()
                .map(BodyMetricResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(metrics);
    }

    @PostMapping
    public ResponseEntity<?> createBodyMetric(@Valid @RequestBody CreateBodyMetricResource resource) {
        var command = LogBodyMetricsCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromBodyMetricCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBodyMetric(@PathVariable Long id,
                                               @Valid @RequestBody UpdateBodyMetricResource resource) {
        var command = UpdateBodyMetricCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromBodyMetricCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }
}
