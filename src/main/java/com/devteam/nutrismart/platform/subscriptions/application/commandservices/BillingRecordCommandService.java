package com.devteam.nutrismart.platform.subscriptions.application.commandservices;

import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.subscriptions.application.commands.CreateBillingRecordCommand;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.BillingRecord;

/**
 * Puerto de entrada (interfaz de servicio de comandos) para la gestión de registros de pago.
 * Define las operaciones de escritura sobre el agregado {@link BillingRecord}.
 */
public interface BillingRecordCommandService {

    /**
     * Procesa el comando para registrar un nuevo pago en el historial de facturación.
     *
     * @param command comando con los datos del pago a registrar
     * @return resultado exitoso con el registro de pago creado, o un fallo tipado
     */
    Result<BillingRecord, BillingRecordCommandFailure> handle(CreateBillingRecordCommand command);
}
