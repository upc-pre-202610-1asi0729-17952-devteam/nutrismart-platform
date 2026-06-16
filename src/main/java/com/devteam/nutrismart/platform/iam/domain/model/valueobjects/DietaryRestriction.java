package com.devteam.nutrismart.platform.iam.domain.model.valueobjects;

/**
 * Restricciones dietéticas que puede declarar un usuario para que la plataforma
 * filtre y adapte las recomendaciones alimenticias de acuerdo con sus necesidades o creencias.
 */
public enum DietaryRestriction {
    /** El usuario no consume productos con lactosa. */
    LACTOSE_FREE,
    /** El usuario no consume gluten (celiaquía o preferencia). */
    GLUTEN_FREE,
    /** El usuario sigue una dieta vegana estricta sin productos de origen animal. */
    VEGAN,
    /** El usuario sigue una dieta vegetariana sin carne, pero puede consumir lácteos y huevos. */
    VEGETARIAN,
    /** El usuario presenta alergia o intolerancia a los frutos secos. */
    NUT_FREE,
    /** El usuario no consume mariscos ni productos del mar. */
    SEAFOOD_FREE,
    /** El usuario sigue las normas alimentarias kosher. */
    KOSHER,
    /** El usuario sigue las normas alimentarias halal. */
    HALAL
}
