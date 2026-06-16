package com.devteam.nutrismart.platform.analytics.application.ports;

import java.util.Optional;

/**
 * Puerto de salida que permite al módulo de analíticas obtener la información básica
 * de un usuario y sus objetivos de macronutrientes desde el contexto de identidad (IAM).
 * Es implementado por un adaptador ACL en la capa de infraestructura.
 */
public interface UserDataPort {

    /**
     * DTO interno que transporta los datos esenciales del usuario necesarios para
     * construir el agregado {@code Analytics}.
     *
     * @param id            identificador único del usuario
     * @param firstName     nombre del usuario
     * @param lastName      apellido del usuario
     * @param proteinTarget objetivo diario de proteínas en gramos
     * @param carbsTarget   objetivo diario de carbohidratos en gramos
     * @param fatTarget     objetivo diario de grasas en gramos
     */
    record UserSummaryData(
            Long id,
            String firstName,
            String lastName,
            double proteinTarget,
            double carbsTarget,
            double fatTarget
    ) {}

    /**
     * Recupera el resumen de datos del usuario con el identificador indicado.
     *
     * @param userId identificador del usuario a consultar
     * @return un {@link Optional} con los datos del usuario, o vacío si no existe
     */
    Optional<UserSummaryData> getUserSummary(Long userId);
}
