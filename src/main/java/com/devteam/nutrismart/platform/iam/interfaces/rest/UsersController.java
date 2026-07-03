package com.devteam.nutrismart.platform.iam.interfaces.rest;

import com.devteam.nutrismart.platform.iam.application.commandservices.UserCommandService;
import com.devteam.nutrismart.platform.iam.application.commands.ChangePasswordCommand;
import com.devteam.nutrismart.platform.iam.application.commands.DeleteUserCommand;
import com.devteam.nutrismart.platform.iam.application.commands.UpdateNotificationPreferencesCommand;
import com.devteam.nutrismart.platform.iam.application.queries.GetUserByIdQuery;
import com.devteam.nutrismart.platform.iam.application.queryservices.UserQueryService;
import com.devteam.nutrismart.platform.shared.infrastructure.security.CurrentUserGuard;
import com.devteam.nutrismart.platform.iam.interfaces.rest.resources.ChangePasswordResource;
import com.devteam.nutrismart.platform.iam.interfaces.rest.resources.CreateUserResource;
import com.devteam.nutrismart.platform.iam.interfaces.rest.resources.NotificationPreferencesResource;
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

/**
 * Controlador REST para la gestión de usuarios de la plataforma NutriSmart.
 * Expone los endpoints del recurso {@code /api/v1/users}.
 * <p>
 * Todos los endpoints por {@code {id}} verifican la propiedad mediante {@link CurrentUserGuard}:
 * un usuario autenticado solo puede consultar o modificar su propia cuenta (403 en caso contrario).
 * </p>
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management endpoints")
public class UsersController {

    private final UserCommandService commandService;
    private final UserQueryService queryService;
    private final CurrentUserGuard currentUser;

    public UsersController(UserCommandService commandService, UserQueryService queryService,
                           CurrentUserGuard currentUser) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.currentUser = currentUser;
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
        currentUser.ensureOwner(id);
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
        currentUser.ensureOwner(id);
        var command = UpdateUserCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityFromUserCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }

    @Operation(summary = "Change password", description = "Changes the password of an authenticated user after verifying the current password.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Password changed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data or current password is incorrect"),
        @ApiResponse(responseCode = "401", description = "Unauthorized — valid JWT token required"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PatchMapping("/{id}/password")
    public ResponseEntity<?> changePassword(
            @Parameter(description = "Unique identifier of the user", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordResource resource) {
        currentUser.ensureOwner(id);
        var command = new ChangePasswordCommand(id, resource.currentPassword(), resource.newPassword());
        var result = commandService.handle(command);
        return ResponseEntityFromUserCommandResultAssembler.toResponseEntityFromUpdateResult(result);
    }

    @Operation(summary = "Update notification preferences", description = "Updates the user's notification preferences. Null fields are left unchanged.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Notification preferences updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized — valid JWT token required"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PatchMapping("/{id}/notification-preferences")
    public ResponseEntity<?> updateNotificationPreferences(
            @Parameter(description = "Unique identifier of the user", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody NotificationPreferencesResource resource) {
        currentUser.ensureOwner(id);
        var command = new UpdateNotificationPreferencesCommand(
                id,
                resource.dailyLogReminder(),
                resource.weeklyProgressReport(),
                resource.mealSuggestions(),
                resource.streakAlerts(),
                resource.marketingEmails()
        );
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
        currentUser.ensureOwner(id);
        var command = new DeleteUserCommand(id);
        var result = commandService.handle(command);
        return ResponseEntityFromUserCommandResultAssembler.toResponseEntityFromDeleteResult(result);
    }
}
