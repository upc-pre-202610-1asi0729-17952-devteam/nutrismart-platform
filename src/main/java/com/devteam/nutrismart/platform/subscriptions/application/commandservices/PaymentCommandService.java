package com.devteam.nutrismart.platform.subscriptions.application.commandservices;

import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.subscriptions.application.commands.ProcessPaymentCommand;
import com.devteam.nutrismart.platform.subscriptions.domain.model.services.PaymentGateway;

/**
 * Puerto de entrada (interfaz de servicio de comandos) para el procesamiento de cobros.
 * Orquesta la resolución del método de pago y la interacción con la pasarela.
 */
public interface PaymentCommandService {

    /**
     * Procesa el comando de cobro contra la pasarela de pago.
     *
     * @param command comando con el usuario, método de pago e importe a cobrar
     * @return resultado exitoso con el {@link PaymentGateway.PaymentResult} del intento
     *         (que puede indicar un cargo rechazado), o un fallo tipado si no se pudo intentar
     */
    Result<PaymentGateway.PaymentResult, PaymentCommandFailure> handle(ProcessPaymentCommand command);
}
