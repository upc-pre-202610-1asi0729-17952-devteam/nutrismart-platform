package com.devteam.nutrismart.platform.subscriptions.domain.model.repositories;

import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.PaymentMethod;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio del dominio para el agregado {@link PaymentMethod}.
 * Define las operaciones de persistencia necesarias para gestionar los métodos de pago.
 * La implementación concreta reside en la capa de infraestructura.
 */
public interface PaymentMethodRepository {

    /**
     * Busca un método de pago por su identificador único.
     *
     * @param id identificador del método de pago
     * @return un {@link Optional} con el método encontrado, o vacío si no existe
     */
    Optional<PaymentMethod> findById(Long id);

    /**
     * Recupera todos los métodos de pago asociados a un usuario.
     *
     * @param userId identificador del usuario
     * @return lista de métodos de pago del usuario; vacía si no tiene ninguno
     */
    List<PaymentMethod> findByUserId(Long userId);

    /**
     * Devuelve el identificador del Customer de Stripe asociado a cualquier método de
     * pago previo del usuario, si existe. Permite reutilizar un único Customer por
     * usuario (modelo canónico 1 customer ↔ N tarjetas) en lugar de crear uno por cobro.
     *
     * @param userId identificador del usuario
     * @return un {@link Optional} con el {@code stripeCustomerId} existente, o vacío si no hay
     */
    Optional<String> findAnyStripeCustomerIdByUserId(Long userId);

    /**
     * Persiste un nuevo método de pago o actualiza uno existente.
     *
     * @param paymentMethod método de pago a guardar
     * @return el método guardado, con el identificador asignado
     */
    PaymentMethod save(PaymentMethod paymentMethod);

    /**
     * Indica si existe un método de pago con el identificador indicado.
     *
     * @param id identificador del método de pago
     * @return {@code true} si existe
     */
    boolean existsById(Long id);

    /**
     * Elimina un método de pago por su identificador.
     *
     * @param id identificador del método de pago a eliminar
     */
    void deleteById(Long id);
}
