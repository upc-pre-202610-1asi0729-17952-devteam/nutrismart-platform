package com.devteam.nutrismart.platform.subscriptions.application.commandservices;

import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.subscriptions.application.commands.CreateSubscriptionCommand;
import com.devteam.nutrismart.platform.subscriptions.application.commands.UpdateSubscriptionCommand;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.Subscription;

/**
 * Puerto de entrada (interfaz de servicio de comandos) para la gestión de suscripciones.
 * Define las operaciones de escritura que pueden modificar el estado del agregado
 * {@link Subscription}.
 */
public interface SubscriptionCommandService {

    /**
     * Procesa el comando para crear una nueva suscripción.
     * Falla si el usuario ya posee una suscripción activa.
     *
     * @param command comando con los datos de la nueva suscripción
     * @return resultado exitoso con la suscripción creada, o un fallo tipado
     */
    Result<Subscription, SubscriptionCommandFailure> handle(CreateSubscriptionCommand command);

    /**
     * Procesa el comando para actualizar una suscripción existente.
     *
     * @param command comando con el identificador y los nuevos valores de la suscripción
     * @return resultado exitoso con la suscripción actualizada, o un fallo si no se encontró
     */
    Result<Subscription, SubscriptionCommandFailure> handle(UpdateSubscriptionCommand command);
}
