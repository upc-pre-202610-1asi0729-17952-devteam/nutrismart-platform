package com.devteam.nutrismart.platform.iam.interfaces.acl;

import java.util.List;
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

    /**
     * Perfil dietético del usuario expuesto a otros contextos acotados
     * (filtrado/ranking de platos). Solo incluye la información necesaria para
     * esas decisiones, sin revelar el aggregate {@code User}.
     *
     * @param goal               objetivo del usuario (nombre del enum) o {@code null}
     * @param restrictions       restricciones dietéticas (nombres de enum)
     * @param dailyCalorieTarget objetivo calórico diario o {@code null}
     */
    record UserDietaryProfileData(
            String goal,
            List<String> restrictions,
            Double dailyCalorieTarget
    ) {}

    /**
     * Obtiene el perfil dietético del usuario para filtrado y ranking en otros contextos.
     *
     * @param userId identificador único del usuario
     * @return {@code Optional} con el perfil dietético, o vacío si el usuario no existe
     */
    Optional<UserDietaryProfileData> getUserDietaryProfile(Long userId);
}
