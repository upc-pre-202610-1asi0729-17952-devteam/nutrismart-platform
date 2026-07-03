package com.devteam.nutrismart.platform.subscriptions.interfaces.rest;

import com.devteam.nutrismart.platform.subscriptions.application.commands.ProcessPaymentCommand;
import com.devteam.nutrismart.platform.subscriptions.application.commandservices.PaymentCommandService;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.resources.ProcessPaymentResource;
import com.devteam.nutrismart.platform.subscriptions.interfaces.rest.transform.ResponseEntityFromPaymentCommandResultAssembler;
import com.devteam.nutrismart.platform.shared.infrastructure.security.CurrentUserGuard;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST que expone el procesamiento de cobros de suscripción.
 * El cobro real se realiza en el backend a través de la pasarela Stripe;
 * el frontend nunca maneja la clave secreta.
 */
@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payments", description = "Subscription payment processing endpoints")
public class PaymentsController {

    private final PaymentCommandService commandService;
    private final CurrentUserGuard currentUser;

    public PaymentsController(PaymentCommandService commandService, CurrentUserGuard currentUser) {
        this.commandService = commandService;
        this.currentUser = currentUser;
    }

    @Operation(summary = "Charge a subscription payment",
               description = "Charges the referenced payment method via Stripe. Returns success with a transaction id, "
                       + "or success=false with an error code when the card is declined.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Charge attempt processed (may be accepted or declined)"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "Payment method not found")
    })
    @PostMapping("/charge")
    public ResponseEntity<?> charge(@Valid @RequestBody ProcessPaymentResource resource) {
        currentUser.ensureOwner(resource.userId());
        var command = new ProcessPaymentCommand(
                resource.userId(),
                resource.plan(),
                resource.paymentMethodId(),
                resource.amount(),
                resource.currency() == null || resource.currency().isBlank() ? "USD" : resource.currency()
        );
        var result = commandService.handle(command);
        return ResponseEntityFromPaymentCommandResultAssembler.toResponseEntityFromChargeResult(result);
    }
}
