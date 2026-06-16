package com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.valueobjects.AdherenceStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

/**
 * Agregado raíz que modela el progreso conductual de un usuario en su plan nutricional.
 * Registra la racha de días consecutivos cumplidos, las faltas consecutivas, la tasa de
 * cumplimiento semanal, el estado de adherencia y el historial de fechas en que se alcanzó
 * el objetivo diario.
 */
public class BehavioralProgress {

    private Long id;
    private Long userId;
    private AdherenceStatus adherenceStatus;
    private Integer streak;
    private Integer consecutiveMisses;
    private Double weeklyCompletionRate;
    private Instant lastEvaluatedAt;
    private TreeSet<LocalDate> goalMetDates;

    private BehavioralProgress() {}

    /**
     * Fábrica que inicializa un nuevo registro de progreso conductual para el usuario indicado.
     * Todos los contadores parten en cero y el estado de adherencia se establece en {@code ON_TRACK}.
     *
     * @param userId identificador del usuario propietario del progreso
     * @return nueva instancia de {@code BehavioralProgress} con valores iniciales
     */
    public static BehavioralProgress create(Long userId) {
        BehavioralProgress bp = new BehavioralProgress();
        bp.userId = userId;
        bp.adherenceStatus = AdherenceStatus.ON_TRACK;
        bp.streak = 0;
        bp.consecutiveMisses = 0;
        bp.weeklyCompletionRate = 0.0;
        bp.lastEvaluatedAt = Instant.now();
        bp.goalMetDates = new TreeSet<>();
        return bp;
    }

    /**
     * Rehidrata un registro de progreso conductual existente desde la capa de persistencia.
     *
     * @param id                  identificador persistido del registro
     * @param userId              identificador del usuario propietario
     * @param adherenceStatus     estado de adherencia almacenado
     * @param streak              racha de días consecutivos cumplidos
     * @param consecutiveMisses   número de días consecutivos sin cumplir el objetivo
     * @param weeklyCompletionRate tasa de cumplimiento semanal (0.0 a 1.0)
     * @param lastEvaluatedAt     marca de tiempo de la última evaluación
     * @param goalMetDates        conjunto de fechas en que se alcanzó el objetivo diario
     * @return instancia de {@code BehavioralProgress} reconstituida
     */
    public static BehavioralProgress rehydrate(
            Long id,
            Long userId,
            AdherenceStatus adherenceStatus,
            Integer streak,
            Integer consecutiveMisses,
            Double weeklyCompletionRate,
            Instant lastEvaluatedAt,
            Set<LocalDate> goalMetDates) {
        BehavioralProgress bp = new BehavioralProgress();
        bp.id = id;
        bp.userId = userId;
        bp.adherenceStatus = adherenceStatus;
        bp.streak = streak;
        bp.consecutiveMisses = consecutiveMisses;
        bp.weeklyCompletionRate = weeklyCompletionRate;
        bp.lastEvaluatedAt = lastEvaluatedAt;
        bp.goalMetDates = goalMetDates != null ? new TreeSet<>(goalMetDates) : new TreeSet<>();
        return bp;
    }

    /**
     * Actualiza los indicadores de progreso conductual y recalcula el estado de adherencia
     * en función de los días consecutivos sin cumplir el objetivo.
     *
     * @param adherenceStatus     estado de adherencia propuesto (puede ser sobreescrito por el cálculo interno)
     * @param streak              nueva racha de días consecutivos cumplidos
     * @param consecutiveMisses   nuevo número de días consecutivos sin cumplir
     * @param weeklyCompletionRate nueva tasa de cumplimiento semanal
     * @param lastEvaluatedAt     marca de tiempo de la evaluación; si es {@code null} se usa el instante actual
     */
    public void update(AdherenceStatus adherenceStatus, Integer streak, Integer consecutiveMisses, Double weeklyCompletionRate, Instant lastEvaluatedAt) {
        this.streak = streak;
        this.consecutiveMisses = consecutiveMisses;
        this.weeklyCompletionRate = weeklyCompletionRate;
        this.adherenceStatus = calculateAdherenceStatus(consecutiveMisses);
        this.lastEvaluatedAt = lastEvaluatedAt != null ? lastEvaluatedAt : Instant.now();
    }

    /**
     * Registra una fecha en la que el usuario alcanzó su objetivo diario.
     *
     * @param date fecha a agregar al historial de objetivos cumplidos
     */
    public void markGoalMet(LocalDate date) {
        goalMetDates.add(date);
    }

    private AdherenceStatus calculateAdherenceStatus(int consecutiveMisses) {
        if (consecutiveMisses == 0) return AdherenceStatus.ON_TRACK;
        if (consecutiveMisses <= 2) return AdherenceStatus.AT_RISK;
        return AdherenceStatus.DROPPED;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public AdherenceStatus getAdherenceStatus() { return adherenceStatus; }
    public Integer getStreak() { return streak; }
    public Integer getConsecutiveMisses() { return consecutiveMisses; }
    public Double getWeeklyCompletionRate() { return weeklyCompletionRate; }
    public Instant getLastEvaluatedAt() { return lastEvaluatedAt; }
    public TreeSet<LocalDate> getGoalMetDates() { return goalMetDates; }
}
