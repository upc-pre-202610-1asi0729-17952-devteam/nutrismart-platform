package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.WeatherSnapshotCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllWeatherSnapshotsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetWeatherSnapshotByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.WeatherSnapshotQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateWeatherSnapshotResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.SyncWeatherSnapshotResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.UpdateWeatherSnapshotResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.WeatherSnapshotResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.CreateWeatherSnapshotCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.ResponseEntityFromWeatherSnapshotCommandResultAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.SyncWeatherSnapshotCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.UpdateWeatherSnapshotCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.WeatherSnapshotResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/weather-snapshots")
@Tag(name = "Weather Snapshots", description = "Weather snapshot management endpoints")
public class WeatherSnapshotsController {

    private final WeatherSnapshotCommandService commandService;
    private final WeatherSnapshotQueryService queryService;

    public WeatherSnapshotsController(WeatherSnapshotCommandService commandService,
                                      WeatherSnapshotQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<WeatherSnapshotResource>> getAll(@RequestParam(required = false) String city) {
        var snapshots = queryService.handle(new GetAllWeatherSnapshotsQuery(city))
                .stream()
                .map(WeatherSnapshotResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(snapshots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeatherSnapshotResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetWeatherSnapshotByIdQuery(id))
                .map(WeatherSnapshotResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateWeatherSnapshotResource resource) {
        var command = CreateWeatherSnapshotCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromWeatherSnapshotCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UpdateWeatherSnapshotResource resource) {
        var command = UpdateWeatherSnapshotCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromWeatherSnapshotCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }

    @PostMapping("/sync")
    public ResponseEntity<?> sync(@Valid @RequestBody SyncWeatherSnapshotResource resource) {
        var command = SyncWeatherSnapshotCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromWeatherSnapshotCommandResultAssembler.toResponseEntityFromSyncResult(result);
    }
}
