package com.devteam.nutrismart.platform.iam.domain.model.valueobjects;

/**
 * Objetivo nutricional primario del usuario dentro de la plataforma.
 * Determina la distribución de macronutrientes y el ajuste calórico aplicado
 * al calcular los objetivos metabólicos del usuario.
 */
public enum UserGoal {
    /** Objetivo de pérdida de peso: se aplica un déficit calórico de 500 kcal/día. */
    WEIGHT_LOSS,
    /** Objetivo de ganancia muscular: se aplica un superávit calórico de 300 kcal/día. */
    MUSCLE_GAIN
}
