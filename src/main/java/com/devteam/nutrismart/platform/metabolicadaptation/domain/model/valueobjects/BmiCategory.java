package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects;

/**
 * Enumeración que clasifica el Índice de Masa Corporal (IMC) de un usuario
 * según los rangos estándar definidos por la Organización Mundial de la Salud.
 */
public enum BmiCategory {
    /** IMC inferior a 18,5 — peso por debajo del rango saludable. */
    UNDERWEIGHT,
    /** IMC entre 18,5 y 24,9 — peso dentro del rango saludable. */
    NORMAL,
    /** IMC entre 25,0 y 29,9 — peso por encima del rango saludable. */
    OVERWEIGHT,
    /** IMC igual o superior a 30,0 — obesidad. */
    OBESE;

    /**
     * Determina la categoría de IMC a partir de un valor numérico calculado.
     *
     * @param bmi valor del Índice de Masa Corporal
     * @return la {@link BmiCategory} correspondiente al valor proporcionado
     */
    public static BmiCategory fromBmi(double bmi) {
        if (bmi < 18.5) return UNDERWEIGHT;
        if (bmi < 25.0) return NORMAL;
        if (bmi < 30.0) return OVERWEIGHT;
        return OBESE;
    }
}
