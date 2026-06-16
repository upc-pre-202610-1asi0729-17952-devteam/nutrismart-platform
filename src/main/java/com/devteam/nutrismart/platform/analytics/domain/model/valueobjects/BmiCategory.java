package com.devteam.nutrismart.platform.analytics.domain.model.valueobjects;

/**
 * Enumeración que clasifica el Índice de Masa Corporal (IMC) de un usuario
 * según los rangos estándar de la Organización Mundial de la Salud (OMS).
 */
public enum BmiCategory {
    /** IMC menor a 18.5: el usuario tiene bajo peso. */
    UNDERWEIGHT,
    /** IMC entre 18.5 y 24.9: peso normal y saludable. */
    NORMAL,
    /** IMC entre 25.0 y 29.9: el usuario tiene sobrepeso. */
    OVERWEIGHT,
    /** IMC de 30.0 o superior: el usuario tiene obesidad. */
    OBESE;

    public static BmiCategory fromBmi(double bmi) {
        if (bmi < 18.5) return UNDERWEIGHT;
        if (bmi < 25.0) return NORMAL;
        if (bmi < 30.0) return OVERWEIGHT;
        return OBESE;
    }
}
