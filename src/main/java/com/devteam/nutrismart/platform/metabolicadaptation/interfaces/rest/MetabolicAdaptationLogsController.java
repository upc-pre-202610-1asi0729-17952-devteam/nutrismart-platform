package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.MetabolicAdaptationLogCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queries.GetAllMetabolicAdaptationLogsQuery;
import com.devteam.nutrismart.platform.metabolicadaptation.application.queryservices.MetabolicAdaptationLogQueryService;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.CreateMetabolicAdaptationLogResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources.MetabolicAdaptationLogResource;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.MetabolicAdaptationLogResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.RecordMetabolicAdaptationCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.transform.ResponseEntityFromMetabolicAdaptationLogCommandResultAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metabolic-adaptation-logs")
@Tag(name = "Metabolic Adaptation Logs", description = "Metabolic adaptation log management endpoints")
public class MetabolicAdaptationLogsController {

    private final MetabolicAdaptationLogCommandService commandService;
    private final MetabolicAdaptationLogQueryService queryService;

    public MetabolicAdaptationLogsController(MetabolicAdaptationLogCommandService commandService,
                                              MetabolicAdaptationLogQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<MetabolicAdaptationLogResource>> getAllMetabolicAdaptationLogs(
            @RequestParam(required = false) Long userId) {
        var logs = queryService.handle(new GetAllMetabolicAdaptationLogsQuery(userId)).stream()
                .map(MetabolicAdaptationLogResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(logs);
    }

    @PostMapping
    public ResponseEntity<?> createMetabolicAdaptationLog(
            @Valid @RequestBody CreateMetabolicAdaptationLogResource resource) {
        var command = RecordMetabolicAdaptationCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromMetabolicAdaptationLogCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }
}
