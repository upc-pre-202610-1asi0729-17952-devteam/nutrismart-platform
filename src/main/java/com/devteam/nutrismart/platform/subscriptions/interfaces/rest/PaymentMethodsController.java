package com.devteam.nutrismart.platform.subscriptions.interfaces.rest;

import com.devteam.nutrismart.platform.subscriptions.application.commandservices.PaymentMethodCommandService;
import com.devteam.nutrismart.platform.subscriptions.application.queries.GetPaymentMethodByIdQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queries.GetPaymentMethodsByUserIdQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queryservices.PaymentMethodQueryService;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.PaymentMethodResource;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.RegisterPaymentMethodResource;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform.PaymentMethodCommandFromResourceAssembler;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform.PaymentMethodResourceFromEntityAssembler;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform.ResponseEntityFromPaymentMethodCommandResultAssembler;
import com.devteam.nutrismart.platform.shared.infrastructure.security.CurrentUserGuard;
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
 * Controlador REST que expone los endpoints de gestión de métodos de pago.
 * Permite registrar, consultar y eliminar métodos de pago tokenizados en Stripe.
 */
@RestController
@RequestMapping("/api/v1/payment-methods")
@Tag(name = "Payment Methods", description = "Payment method management endpoints")
public class PaymentMethodsController {

    private final PaymentMethodCommandService commandService;
    private final PaymentMethodQueryService queryService;
    private final CurrentUserGuard currentUser;

    public PaymentMethodsController(PaymentMethodCommandService commandService,
                                    PaymentMethodQueryService queryService,
                                    CurrentUserGuard currentUser) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.currentUser = currentUser;
    }

    @Operation(summary = "Register a new payment method",
               description = "Registers a payment method tokenized in Stripe (pm_xxx) for a user, storing only safe card metadata.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Payment method registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegisterPaymentMethodResource resource) {
        currentUser.ensureOwner(resource.userId());
        var command = PaymentMethodCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromPaymentMethodCommandResultAssembler.toResponseEntityFromRegisterResult(result);
    }

    @Operation(summary = "Get payment methods by user",
               description = "Retrieves all payment methods registered for a specific user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Payment methods retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<PaymentMethodResource>> getByUser(
            @Parameter(description = "Filter payment methods by user ID", example = "1")
            @RequestParam Long userId) {
        currentUser.ensureOwner(userId);
        var methods = queryService.handle(new GetPaymentMethodsByUserIdQuery(userId));
        return ResponseEntity.ok(methods.stream()
                .map(PaymentMethodResourceFromEntityAssembler::toResourceFromEntity)
                .toList());
    }

    @Operation(summary = "Delete a payment method",
               description = "Removes a payment method from a user's account. Returns 204 on success, 404 if not found.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Payment method deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Payment method not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "Unique identifier of the payment method to delete", example = "1")
            @PathVariable Long id) {
        var owned = queryService.handle(new GetPaymentMethodByIdQuery(id));
        if (owned.isEmpty() || !currentUser.isOwner(owned.get().getUserId())) {
            return ResponseEntity.notFound().build();
        }
        return commandService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
