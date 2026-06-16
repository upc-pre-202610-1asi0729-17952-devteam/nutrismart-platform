package com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.exceptions.DomainInvalidExceptions;

import java.time.LocalDate;

/**
 * Agregado raíz que representa el registro diario de ingesta calórica de un usuario.
 * <p>
 * Encapsula el objetivo calórico diario, las calorías consumidas y las calorías
 * quemadas mediante actividad física para una fecha específica. La creación de nuevas
 * instancias se realiza exclusivamente a través de los métodos de fábrica estáticos,
 * garantizando las invariantes del dominio.
 * </p>
 */
public class DailyIntake {

    private Long id;
    private Long userId;
    private LocalDate date;
    private Double dailyGoal;
    private Double consumed;
    private Double active;

    private DailyIntake() {}

    /**
     * Crea una nueva instancia de {@code DailyIntake} aplicando las reglas de negocio.
     * <p>
     * Valida que el identificador de usuario, la fecha y el objetivo calórico sean
     * valores admisibles antes de construir el agregado.
     * </p>
     *
     * @param userId    identificador único del usuario propietario del registro; no puede ser {@code null}.
     * @param date      fecha a la que corresponde el registro diario; no puede ser {@code null}.
     * @param dailyGoal objetivo calórico diario expresado en kilocalorías; debe ser mayor o igual a cero.
     * @param consumed  calorías consumidas durante el día.
     * @param active    calorías quemadas mediante actividad física durante el día.
     * @return nueva instancia de {@code DailyIntake} lista para ser persistida.
     * @throws DomainInvalidExceptions si {@code userId} o {@code date} son {@code null},
     *                                 o si {@code dailyGoal} es {@code null} o negativo.
     */
    public static DailyIntake create(Long userId, LocalDate date, Double dailyGoal, Double consumed, Double active) {
        if (userId == null) throw new DomainInvalidExceptions("userId must not be null");
        if (date == null) throw new DomainInvalidExceptions("date must not be null");
        if (dailyGoal == null || dailyGoal < 0) throw new DomainInvalidExceptions("dailyGoal must be >= 0");

        DailyIntake intake = new DailyIntake();
        intake.userId = userId;
        intake.date = date;
        intake.dailyGoal = dailyGoal;
        intake.consumed = consumed;
        intake.active = active;
        return intake;
    }

    /**
     * Reconstituye una instancia de {@code DailyIntake} a partir de datos persistidos.
     * <p>
     * Este método omite las validaciones de negocio, ya que los datos provienen de un
     * estado previamente válido almacenado en la capa de persistencia.
     * </p>
     *
     * @param id        identificador único del registro almacenado.
     * @param userId    identificador del usuario propietario.
     * @param date      fecha correspondiente al registro.
     * @param dailyGoal objetivo calórico diario en kilocalorías.
     * @param consumed  calorías consumidas durante el día.
     * @param active    calorías quemadas mediante actividad física.
     * @return instancia de {@code DailyIntake} reconstituida con el estado persistido.
     */
    public static DailyIntake rehydrate(Long id, Long userId, LocalDate date, Double dailyGoal, Double consumed, Double active) {
        DailyIntake intake = new DailyIntake();
        intake.id = id;
        intake.userId = userId;
        intake.date = date;
        intake.dailyGoal = dailyGoal;
        intake.consumed = consumed;
        intake.active = active;
        return intake;
    }

    /**
     * Actualiza los valores calóricos del registro diario.
     * <p>
     * Permite modificar el objetivo calórico, las calorías consumidas y las calorías
     * activas sin alterar el usuario ni la fecha del registro.
     * </p>
     *
     * @param dailyGoal nuevo objetivo calórico diario en kilocalorías.
     * @param consumed  nuevo valor de calorías consumidas durante el día.
     * @param active    nuevo valor de calorías quemadas mediante actividad física.
     */
    public void update(Double dailyGoal, Double consumed, Double active) {
        this.dailyGoal = dailyGoal;
        this.consumed = consumed;
        this.active = active;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public LocalDate getDate() { return date; }
    public Double getDailyGoal() { return dailyGoal; }
    public Double getConsumed() { return consumed; }
    public Double getActive() { return active; }
}
