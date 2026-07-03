package com.devteam.nutrismart.platform.subscriptions.application.queryservices;

import com.devteam.nutrismart.platform.subscriptions.application.queries.GetPaymentMethodByIdQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queries.GetPaymentMethodsByUserIdQuery;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.PaymentMethod;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada (interfaz de servicio de consultas) para los métodos de pago.
 * Define las operaciones de solo lectura sobre el agregado {@link PaymentMethod}.
 */
public interface PaymentMethodQueryService {

    /**
     * Recupera un método de pago por su identificador único.
     *
     * @param query consulta que contiene el identificador del método de pago
     * @return un {@link Optional} con el método, o vacío si no existe
     */
    Optional<PaymentMethod> handle(GetPaymentMethodByIdQuery query);

    /**
     * Recupera todos los métodos de pago asociados a un usuario.
     *
     * @param query consulta que contiene el identificador del usuario
     * @return lista de métodos de pago del usuario; vacía si no tiene ninguno
     */
    List<PaymentMethod> handle(GetPaymentMethodsByUserIdQuery query);
}
