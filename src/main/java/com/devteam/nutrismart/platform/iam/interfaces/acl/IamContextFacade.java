package com.devteam.nutrismart.platform.iam.interfaces.acl;

import java.util.Optional;

/**
 * Fachada Anti-Corruption Layer (ACL) del contexto IAM.
 * Expone una API simplificada para que otros contextos acotados consulten datos
 * del usuario sin acoplarse directamente al dominio IAM.
 */
public interface IamContextFacade {

    /**
     * Resumen de datos del usuario expuesto a otros contextos acotados.
     * Contiene solo la información necesaria para los cálculos de planificación nutricional.
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
     * Obtiene un resumen de los datos del usuario identificado por su ID,
     * incluyendo los objetivos de macronutrientes necesarios para otros contextos.
     *
     * @param userId identificador único del usuario
     * @return {@code Optional} con el resumen del usuario, o vacío si no existe
     */
    Optional<UserSummaryData> getUserSummary(Long userId);
}
