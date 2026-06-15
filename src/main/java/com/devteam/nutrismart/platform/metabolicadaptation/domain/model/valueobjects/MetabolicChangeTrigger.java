package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects;

/**
 * Enumeración que representa el evento que originó un cambio en la adaptación metabólica del usuario.
 * <p>
 * Cada valor indica la causa raíz que desencadenó el recálculo de BMR, TDEE y macronutrientes.
 * </p>
 */
public enum MetabolicChangeTrigger {
    /** El usuario completó el proceso de incorporación inicial a la plataforma. */
    ONBOARDING,
    /** El usuario actualizó información relevante de su perfil (objetivo, nivel de actividad, etc.). */
    PROFILE_CHANGE,
    /** Se registraron nuevas métricas corporales que impactan el metabolismo basal. */
    BODY_METRICS_UPDATE,
    /** El sistema detectó un estancamiento en el progreso del usuario y ajustó los objetivos. */
    STAGNATION_DETECTED
}
