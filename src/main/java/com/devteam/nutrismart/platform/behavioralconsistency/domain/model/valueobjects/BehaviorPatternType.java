package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects;

/**
 * Clasifica el patrón de comportamiento alimentario detectado para un usuario.
 * Se calcula a partir de la tasa de cumplimiento semanal y la racha de días consecutivos.
 */
public enum BehaviorPatternType {
    /** El usuario mantiene un cumplimiento alto y estable de sus objetivos semanales. */
    CONSISTENT,
    /** El usuario está retomando sus hábitos después de un período de incumplimiento. */
    RECOVERING,
    /** El usuario muestra un comportamiento inconsistente sin tendencia clara de mejora. */
    IRREGULAR
}
