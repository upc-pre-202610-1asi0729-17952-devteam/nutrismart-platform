package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.BehaviorPatternType;

import java.time.Instant;

/**
 * Agregado raíz que representa el patrón de comportamiento alimentario detectado para un usuario.
 * Clasifica al usuario como consistente, en recuperación o irregular con base en su tasa
 * de cumplimiento semanal y su racha de días consecutivos completados.
 */
public class EatingBehaviorPattern {

    private Long id;
    private Long userId;
    private BehaviorPatternType patternType;
    private Instant detectedAt;

    private EatingBehaviorPattern() {}

    /**
     * Fábrica que crea un nuevo patrón de comportamiento alimentario para el usuario indicado.
     * El tipo de patrón se calcula automáticamente a partir de la tasa de cumplimiento y la racha.
     *
     * @param userId               identificador del usuario propietario del patrón
     * @param weeklyCompletionRate tasa de cumplimiento semanal (valor entre 0.0 y 1.0)
     * @param streak               racha actual de días consecutivos completados
     * @return nueva instancia de {@code EatingBehaviorPattern}
     */
    public static EatingBehaviorPattern create(Long userId, Double weeklyCompletionRate, Integer streak) {
        EatingBehaviorPattern pattern = new EatingBehaviorPattern();
        pattern.userId = userId;
        pattern.patternType = calculatePatternType(weeklyCompletionRate, streak);
        pattern.detectedAt = Instant.now();
        return pattern;
    }

    /**
     * Rehidrata un patrón existente desde la capa de persistencia sin ejecutar lógica de negocio.
     *
     * @param id          identificador persistido del patrón
     * @param userId      identificador del usuario propietario
     * @param patternType tipo de patrón recuperado de la base de datos
     * @param detectedAt  marca de tiempo en que fue detectado el patrón
     * @return instancia de {@code EatingBehaviorPattern} reconstituida
     */
    public static EatingBehaviorPattern rehydrate(Long id, Long userId, BehaviorPatternType patternType, Instant detectedAt) {
        EatingBehaviorPattern pattern = new EatingBehaviorPattern();
        pattern.id = id;
        pattern.userId = userId;
        pattern.patternType = patternType;
        pattern.detectedAt = detectedAt;
        return pattern;
    }

    /**
     * Actualiza el tipo de patrón y restablece la marca de tiempo de detección al momento actual.
     *
     * @param patternType nuevo tipo de patrón de comportamiento alimentario
     */
    public void update(BehaviorPatternType patternType) {
        this.patternType = patternType;
        this.detectedAt = Instant.now();
    }

    private static BehaviorPatternType calculatePatternType(Double weeklyCompletionRate, Integer streak) {
        if (weeklyCompletionRate >= 0.71) return BehaviorPatternType.CONSISTENT;
        if (streak != null && streak > 0) return BehaviorPatternType.RECOVERING;
        return BehaviorPatternType.IRREGULAR;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public BehaviorPatternType getPatternType() { return patternType; }
    public Instant getDetectedAt() { return detectedAt; }
}
