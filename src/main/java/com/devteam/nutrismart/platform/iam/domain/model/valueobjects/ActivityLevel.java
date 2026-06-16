package com.devteam.nutrismart.platform.iam.domain.model.valueobjects;

/**
 * Nivel de actividad física del usuario, utilizado para calcular el gasto energético total (TDEE)
 * a partir de la tasa metabólica basal (BMR). Cada nivel asocia un multiplicador estándar
 * basado en la fórmula de Harris-Benedict revisada.
 */
public enum ActivityLevel {
    /** Usuario con trabajo sedentario y sin ejercicio regular (multiplicador 1.2). */
    SEDENTARY(1.2),
    /** Usuario con ejercicio ligero o moderado de 1 a 3 días a la semana (multiplicador 1.375). */
    MODERATE(1.375),
    /** Usuario que realiza ejercicio moderado de 3 a 5 días a la semana (multiplicador 1.55). */
    ACTIVE(1.55),
    /** Usuario que realiza ejercicio intenso 6 o 7 días a la semana (multiplicador 1.725). */
    VERY_ACTIVE(1.725);

    private final double multiplier;

    ActivityLevel(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
