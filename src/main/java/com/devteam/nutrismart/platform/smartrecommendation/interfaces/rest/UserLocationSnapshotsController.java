package com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.LocationSnapshotCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetAllLocationSnapshotsQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetLastLocationSnapshotByUserIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queries.GetLocationSnapshotByIdQuery;
import com.devteam.nutrismart.platform.smartrecommendation.application.queryservices.LocationSnapshotQueryService;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.CreateLocationSnapshotResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.resources.LocationSnapshotResource;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.CreateLocationSnapshotCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.LocationSnapshotResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.smartrecommendation.interfaces.rest.transform.ResponseEntityFromLocationSnapshotCommandResultAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-location-snapshots")
@Tag(name = "User Location Snapshots", description = "User location snapshot management endpoints")
public class UserLocationSnapshotsController {

    private final LocationSnapshotCommandService commandService;
    private final LocationSnapshotQueryService queryService;

    public UserLocationSnapshotsController(LocationSnapshotCommandService commandService,
                                           LocationSnapshotQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<LocationSnapshotResource>> getAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(name = "_sort", required = false) String sort,
            @RequestParam(name = "_order", required = false) String order,
            @RequestParam(name = "_limit", required = false) Integer limit) {

        if (userId != null && "recordedAt".equals(sort) && "desc".equals(order) && Integer.valueOf(1).equals(limit)) {
            return queryService.handle(new GetLastLocationSnapshotByUserIdQuery(userId))
                    .map(snapshot -> ResponseEntity.ok(List.of(LocationSnapshotResourceFromEntityAssembler.toResourceFromEntity(snapshot))))
                    .orElse(ResponseEntity.ok(List.of()));
        }

        var snapshots = queryService.handle(new GetAllLocationSnapshotsQuery(userId))
                .stream()
                .map(LocationSnapshotResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(snapshots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationSnapshotResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetLocationSnapshotByIdQuery(id))
                .map(LocationSnapshotResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateLocationSnapshotResource resource) {
        var command = CreateLocationSnapshotCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromLocationSnapshotCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }
}
