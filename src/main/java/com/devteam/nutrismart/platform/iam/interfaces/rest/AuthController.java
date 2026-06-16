package com.devteam.nutrismart.platform.iam.interfaces.rest;

import com.devteam.nutrismart.platform.iam.application.commandservices.AuthCommandService;
import com.devteam.nutrismart.platform.iam.application.commandservices.PasswordResetCommandFailure;
import com.devteam.nutrismart.platform.iam.application.commandservices.PasswordResetCommandService;
import com.devteam.nutrismart.platform.iam.application.commands.AuthenticateCommand;
import com.devteam.nutrismart.platform.iam.application.commands.RequestPasswordResetCommand;
import com.devteam.nutrismart.platform.iam.application.commands.ResetPasswordCommand;
import com.devteam.nutrismart.platform.iam.application.queries.GetUserByEmailQuery;
import com.devteam.nutrismart.platform.iam.application.queryservices.UserQueryService;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.EmailAddress;
import com.devteam.nutrismart.platform.iam.interfaces.rest.resources.ForgotPasswordResource;
import com.devteam.nutrismart.platform.iam.interfaces.rest.resources.LoginResource;
import com.devteam.nutrismart.platform.iam.interfaces.rest.resources.ResetPasswordResource;
import com.devteam.nutrismart.platform.iam.interfaces.rest.transform.ResponseEntityFromAuthCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador REST para los endpoints de autenticación y restablecimiento de contraseña.
 * Expone los recursos públicos del contexto IAM bajo {@code /api/v1/auth}.
 */
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Authentication endpoints")
public class AuthController {

    private final UserQueryService userQueryService;
    private final AuthCommandService authCommandService;
    private final PasswordResetCommandService passwordResetCommandService;

    public AuthController(UserQueryService userQueryService,
                          AuthCommandService authCommandService,
                          PasswordResetCommandService passwordResetCommandService) {
        this.userQueryService = userQueryService;
        this.authCommandService = authCommandService;
        this.passwordResetCommandService = passwordResetCommandService;
    }

    @Operation(summary = "Check if email is registered", description = "Returns whether the given email address is already associated with an account.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Result indicating whether the email exists")
    })
    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(
            @Parameter(description = "Email address to check", example = "user@example.com")
            @RequestParam String email) {
        try {
            boolean exists = userQueryService.handle(new GetUserByEmailQuery(new EmailAddress(email))).isPresent();
            return ResponseEntity.ok(Map.of("exists", exists));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(Map.of("exists", false));
        }
    }

    @Operation(summary = "Authenticate user", description = "Validates credentials and returns a JWT token along with basic user information.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Authentication successful — JWT token returned"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Invalid email or password")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginResource resource) {
        var command = new AuthenticateCommand(resource.email(), resource.password());
        return ResponseEntityFromAuthCommandResultAssembler.toResponseEntity(authCommandService.handle(command));
    }

    @Operation(summary = "Request password reset", description = "Sends a password reset link to the provided email address if it is registered. Always returns 200 to avoid email enumeration.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reset email sent if address is registered")
    })
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordResource resource) {
        passwordResetCommandService.handle(new RequestPasswordResetCommand(resource.email()));
        return ResponseEntity.ok(Map.of("message", "If that email is registered, you will receive a reset link shortly."));
    }

    @Operation(summary = "Reset password", description = "Sets a new password for the user identified by the reset token. The token must be valid and not expired.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Password updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid or already used reset token, or invalid data"),
        @ApiResponse(responseCode = "410", description = "Reset link has expired")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordResource resource) {
        var result = passwordResetCommandService.handle(new ResetPasswordCommand(resource.token(), resource.newPassword()));
        return result.fold(
                v -> ResponseEntity.ok(Map.of("message", "Password updated successfully.")),
                failure -> switch (failure) {
                    case PasswordResetCommandFailure.TokenExpired f ->
                            ResponseEntity.status(410).body(Map.of("message", "Reset link has expired. Please request a new one."));
                    case PasswordResetCommandFailure.InvalidToken f ->
                            ResponseEntity.status(400).body(Map.of("message", "Invalid or already used reset link."));
                    case PasswordResetCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
