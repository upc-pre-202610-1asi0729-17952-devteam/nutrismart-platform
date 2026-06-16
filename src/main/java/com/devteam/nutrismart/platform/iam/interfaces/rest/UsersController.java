package com.devteam.nutrismart.platform.iam.interfaces.rest;

import com.devteam.nutrismart.platform.iam.application.commandservices.UserCommandService;
import com.devteam.nutrismart.platform.iam.application.commands.DeleteUserCommand;
import com.devteam.nutrismart.platform.iam.application.queries.GetAllUsersQuery;
import com.devteam.nutrismart.platform.iam.application.queries.GetUserByIdQuery;
import com.devteam.nutrismart.platform.iam.application.queryservices.UserQueryService;
import com.devteam.nutrismart.platform.iam.interfaces.rest.resources.CreateUserResource;
import com.devteam.nutrismart.platform.iam.interfaces.rest.resources.UpdateUserResource;
import com.devteam.nutrismart.platform.iam.interfaces.rest.resources.UserResource;
import com.devteam.nutrismart.platform.iam.interfaces.rest.transform.CreateUserCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.iam.interfaces.rest.transform.ResponseEntityFromUserCommandResultAssembler;
import com.devteam.nutrismart.platform.iam.interfaces.rest.transform.UpdateUserCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
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
 * Controlador REST para la gestión de usuarios de la plataforma NutriSmart.
 * Expone los endpoints CRUD del recurso {@code /api/v1/users}.
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management endpoints")
public class UsersController {

    private final UserCommandService commandService;
    private final UserQueryService queryService;

    public UsersController(UserCommandService commandService, UserQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all registered users in the platform.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized — valid JWT token required"),
        @ApiResponse(responseCode = "403", description = "Forbidden — insufficient permissions")
    })
    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var users = queryService.handle(new GetAllUsersQuery()).stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a single user by their unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found and returned"),
        @ApiResponse(responseCode = "401", description = "Unauthorized — valid JWT token required"),
        @ApiResponse(responseCode = "403", description = "Forbidden — insufficient permissions"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getUserById(
            @Parameter(description = "Unique identifier of the user", example = "1")
            @PathVariable Long id) {
        return queryService.handle(new GetUserByIdQuery(id))
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new user", description = "Registers a new user account with profile and biometric data. Metabolic targets are calculated automatically.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "409", description = "Email address already registered")
    })
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserResource resource) {
        var command = CreateUserCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromUserCommandResultAssembler.toResponseEntityFromCreateResult(result);
    }

    @Operation(summary = "Update user profile", description = "Updates the profile of an existing user. Metabolic targets are recalculated based on the new data.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized — valid JWT token required"),
        @ApiResponse(responseCode = "403", description = "Forbidden — insufficient permissions"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "409", description = "Email address already in use by another account")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @Parameter(description = "Unique identifier of the user to update", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserResource resource) {
        var command = UpdateUserCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromUserCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }

    @Operation(summary = "Delete user", description = "Permanently deletes a user account by their unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized — valid JWT token required"),
        @ApiResponse(responseCode = "403", description = "Forbidden — insufficient permissions"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @Parameter(description = "Unique identifier of the user to delete", example = "1")
            @PathVariable Long id) {
        var command = new DeleteUserCommand(id);
        var result = commandService.handle(command);
        return ResponseEntityFromUserCommandResultAssembler.toResponseEntityFromDeleteResult(result);
    }
}
