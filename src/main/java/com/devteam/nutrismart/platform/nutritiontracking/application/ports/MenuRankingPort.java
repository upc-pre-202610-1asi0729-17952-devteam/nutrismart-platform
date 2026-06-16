package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

import java.util.List;

/**
 * Puerto de salida para la clasificación y puntuación de platos de un menú de restaurante.
 * <p>
 * Define el contrato que deben cumplir los adaptadores que se integran con servicios externos
 * (p. ej., modelos de lenguaje de gran escala) para evaluar la compatibilidad de los platos
 * de un menú con el perfil nutricional y las restricciones dietéticas del usuario,
 * asignándoles una puntuación de compatibilidad y generando una justificación.
 * </p>
 */
public interface MenuRankingPort {

    /**
     * Clasifica los platos de un menú según la compatibilidad con el perfil del usuario.
     *
     * @param dishes          lista de platos candidatos extraídos del menú de restaurante.
     * @param existingMatches lista de alimentos del catálogo interno que ya han sido emparejados
     *                        con alguno de los platos del menú.
     * @param profile         datos del perfil del usuario, incluyendo objetivo calórico,
     *                        restricciones dietéticas y calorías restantes del día.
     * @return lista de {@link RankedDishData} con los platos evaluados, su puntuación de
     *         compatibilidad, justificación y estimación nutricional; ordenada de mayor
     *         a menor compatibilidad.
     */
    List<RankedDishData> rankMenuDishes(
            List<MenuDishCandidate> dishes,
            List<FoodItemCandidate> existingMatches,
            UserProfileData profile
    );
}
