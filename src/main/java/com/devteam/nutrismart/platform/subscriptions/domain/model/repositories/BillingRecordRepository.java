package com.devteam.nutrismart.platform.subscriptions.domain.model.repositories;

import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.BillingRecord;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio del dominio para el agregado {@link BillingRecord}.
 * Define las operaciones de persistencia necesarias para gestionar el historial de pagos.
 * La implementación concreta reside en la capa de infraestructura.
 */
public interface BillingRecordRepository {

    /**
     * Busca un registro de pago por su identificador único.
     *
     * @param id identificador del registro de pago
     * @return un {@link Optional} con el registro encontrado, o vacío si no existe
     */
    Optional<BillingRecord> findById(Long id);

    /**
     * Recupera todos los registros de pago del sistema.
     *
     * @return lista de todos los registros; vacía si no hay ninguno
     */
    List<BillingRecord> findAll();

    /**
     * Recupera todos los registros de pago asociados a un usuario.
     *
     * @param userId identificador del usuario
     * @return lista de registros del usuario; vacía si no tiene ninguno
     */
    List<BillingRecord> findByUserId(Long userId);

    /**
     * Persiste un nuevo registro de pago en el repositorio.
     *
     * @param billingRecord registro de pago a guardar
     * @return el registro guardado, con el identificador asignado
     */
    BillingRecord save(BillingRecord billingRecord);
}
