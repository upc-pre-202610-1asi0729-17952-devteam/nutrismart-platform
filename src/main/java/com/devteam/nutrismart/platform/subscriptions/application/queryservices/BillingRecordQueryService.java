package com.devteam.nutrismart.platform.subscriptions.application.queryservices;

import com.devteam.nutrismart.platform.subscriptions.application.queries.GetAllBillingRecordsQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queries.GetBillingRecordByIdQuery;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.BillingRecord;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada (interfaz de servicio de consultas) para el historial de facturación.
 * Define las operaciones de solo lectura sobre el agregado {@link BillingRecord}.
 */
public interface BillingRecordQueryService {

    /**
     * Recupera un registro de pago por su identificador único.
     *
     * @param query consulta que contiene el identificador del registro
     * @return un {@link Optional} con el registro, o vacío si no existe
     */
    Optional<BillingRecord> handle(GetBillingRecordByIdQuery query);

    /**
     * Recupera todos los registros de pago del sistema.
     *
     * @param query consulta sin filtros
     * @return lista de todos los registros de pago
     */
    List<BillingRecord> handle(GetAllBillingRecordsQuery query);

    /**
     * Recupera todos los registros de pago asociados a un usuario específico.
     *
     * @param userId identificador del usuario
     * @return lista de registros de pago del usuario; vacía si no tiene ninguno
     */
    List<BillingRecord> findByUserId(Long userId);
}
