package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates;

import java.time.Instant;

/**
 * Agregado raíz que representa el registro de una actividad física realizada por un usuario.
 * <p>
 * Almacena el tipo de actividad, la duración y las calorías quemadas, permitiendo llevar
 * un historial del gasto energético del usuario para ajustar su plan nutricional.
 * </p>
 */
public class ActivityLog {

    private Long id;
    private Long userId;
    private String activityType;
    private Integer durationMinutes;
    private Double caloriesBurned;
    private Instant timestamp;

    private ActivityLog() {}

    /**
     * Crea un nuevo registro de actividad física con validación de invariantes de dominio.
     *
     * @param userId          identificador del usuario que realizó la actividad
     * @param activityType    descripción del tipo de actividad (p. ej. "running", "cycling")
     * @param durationMinutes duración de la actividad en minutos (debe ser positivo)
     * @param caloriesBurned  calorías quemadas durante la actividad (debe ser no negativo)
     * @param timestamp       instante en que ocurrió la actividad; si es {@code null} se usa el instante actual
     * @return nueva instancia válida de {@code ActivityLog}
     * @throws IllegalArgumentException si userId es nulo, durationMinutes no es positivo o caloriesBurned es negativo
     */
    public static ActivityLog create(Long userId, String activityType, Integer durationMinutes,
                                     Double caloriesBurned, Instant timestamp) {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (durationMinutes == null || durationMinutes <= 0) throw new IllegalArgumentException("durationMinutes must be positive");
        if (caloriesBurned == null || caloriesBurned < 0) throw new IllegalArgumentException("caloriesBurned must be non-negative");
        ActivityLog al = new ActivityLog();
        al.userId = userId;
        al.activityType = activityType;
        al.durationMinutes = durationMinutes;
        al.caloriesBurned = caloriesBurned;
        al.timestamp = timestamp != null ? timestamp : Instant.now();
        return al;
    }

    /**
     * Reconstituye un {@code ActivityLog} desde el almacenamiento de persistencia.
     *
     * @param id              identificador persistido
     * @param userId          identificador del usuario
     * @param activityType    tipo de actividad física
     * @param durationMinutes duración en minutos
     * @param caloriesBurned  calorías quemadas
     * @param timestamp       instante de la actividad
     * @return instancia de {@code ActivityLog} reconstituida
     */
    public static ActivityLog rehydrate(Long id, Long userId, String activityType, Integer durationMinutes,
                                        Double caloriesBurned, Instant timestamp) {
        ActivityLog al = new ActivityLog();
        al.id = id;
        al.userId = userId;
        al.activityType = activityType;
        al.durationMinutes = durationMinutes;
        al.caloriesBurned = caloriesBurned;
        al.timestamp = timestamp;
        return al;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getActivityType() { return activityType; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public Double getCaloriesBurned() { return caloriesBurned; }
    public Instant getTimestamp() { return timestamp; }
}
