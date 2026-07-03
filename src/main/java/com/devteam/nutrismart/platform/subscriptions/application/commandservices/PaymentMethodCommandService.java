package com.devteam.nutrismart.platform.subscriptions.application.commandservices;

import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.subscriptions.application.commands.RegisterPaymentMethodCommand;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.PaymentMethod;

/**
 * Puerto de entrada (interfaz de servicio de comandos) para la gestión de métodos de pago.
 * Define las operaciones de escritura sobre el agregado {@link PaymentMethod}.
 */
public interface PaymentMethodCommandService {

    /**
     * Procesa el comando para registrar un nuevo método de pago.
     *
     * @param command comando con los datos del método de pago a registrar
     * @return resultado exitoso con el método creado, o un fallo tipado
     */
    Result<PaymentMethod, PaymentMethodCommandFailure> handle(RegisterPaymentMethodCommand command);

    /**
     * Elimina un método de pago por su identificador.
     *
     * @param id identificador del método de pago a eliminar
     * @return {@code true} si existía y fue eliminado; {@code false} si no existía
     */
    boolean delete(Long id);
}
