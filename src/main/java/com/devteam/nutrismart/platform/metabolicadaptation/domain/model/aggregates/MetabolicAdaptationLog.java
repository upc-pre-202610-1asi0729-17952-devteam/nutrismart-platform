package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.MetabolicChangeTrigger;

import java.time.Instant;

/**
 * Agregado raíz que representa una entrada en el historial de adaptación metabólica de un usuario.
 * <p>
 * Cada instancia captura un cambio en el BMR (Tasa Metabólica Basal), TDEE (Gasto Energético Total Diario)
 * y los objetivos de macronutrientes, junto con la causa que originó el recálculo.
 * Este historial permite auditar la evolución del plan nutricional a lo largo del tiempo.
 * </p>
 */
public class MetabolicAdaptationLog {

    private Long id;
    private Long userId;
    private Double previousBMR;
    private Double newBMR;
    private Double previousTDEE;
    private Double newTDEE;
    private String reason;
    private MetabolicChangeTrigger triggeredBy;
    private Double previousCalories;
    private Double newCalories;
    private Double previousProtein;
    private Double newProtein;
    private Double previousCarbs;
    private Double newCarbs;
    private Double previousFat;
    private Double newFat;
    private Instant changedAt;

    private MetabolicAdaptationLog() {}

    /**
     * Crea un nuevo registro de adaptación metabólica sellado con el instante actual.
     *
     * @param userId           identificador del usuario
     * @param previousBMR      valor anterior de BMR en kcal/día
     * @param newBMR           nuevo valor de BMR en kcal/día
     * @param previousTDEE     valor anterior de TDEE en kcal/día
     * @param newTDEE          nuevo valor de TDEE en kcal/día
     * @param reason           descripción textual del motivo del cambio
     * @param triggeredBy      evento que desencadenó el recálculo
     * @param previousCalories objetivo calórico diario anterior
     * @param newCalories      nuevo objetivo calórico diario
     * @param previousProtein  objetivo de proteínas anterior en gramos
     * @param newProtein       nuevo objetivo de proteínas en gramos
     * @param previousCarbs    objetivo de carbohidratos anterior en gramos
     * @param newCarbs         nuevo objetivo de carbohidratos en gramos
     * @param previousFat      objetivo de grasas anterior en gramos
     * @param newFat           nuevo objetivo de grasas en gramos
     * @return nueva instancia válida de {@code MetabolicAdaptationLog}
     * @throws IllegalArgumentException si userId es nulo
     */
    public static MetabolicAdaptationLog create(Long userId, Double previousBMR, Double newBMR,
                                                Double previousTDEE, Double newTDEE, String reason,
                                                MetabolicChangeTrigger triggeredBy,
                                                Double previousCalories, Double newCalories,
                                                Double previousProtein, Double newProtein,
                                                Double previousCarbs, Double newCarbs,
                                                Double previousFat, Double newFat) {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        MetabolicAdaptationLog log = new MetabolicAdaptationLog();
        log.userId = userId;
        log.previousBMR = previousBMR;
        log.newBMR = newBMR;
        log.previousTDEE = previousTDEE;
        log.newTDEE = newTDEE;
        log.reason = reason;
        log.triggeredBy = triggeredBy;
        log.previousCalories = previousCalories;
        log.newCalories = newCalories;
        log.previousProtein = previousProtein;
        log.newProtein = newProtein;
        log.previousCarbs = previousCarbs;
        log.newCarbs = newCarbs;
        log.previousFat = previousFat;
        log.newFat = newFat;
        log.changedAt = Instant.now();
        return log;
    }

    /**
     * Reconstituye un {@code MetabolicAdaptationLog} desde el almacenamiento de persistencia.
     *
     * @param id               identificador persistido
     * @param userId           identificador del usuario
     * @param previousBMR      BMR anterior en kcal/día
     * @param newBMR           nuevo BMR en kcal/día
     * @param previousTDEE     TDEE anterior en kcal/día
     * @param newTDEE          nuevo TDEE en kcal/día
     * @param reason           motivo textual del cambio
     * @param triggeredBy      evento desencadenante
     * @param previousCalories objetivo calórico anterior
     * @param newCalories      nuevo objetivo calórico
     * @param previousProtein  objetivo de proteínas anterior en g
     * @param newProtein       nuevo objetivo de proteínas en g
     * @param previousCarbs    objetivo de carbohidratos anterior en g
     * @param newCarbs         nuevo objetivo de carbohidratos en g
     * @param previousFat      objetivo de grasas anterior en g
     * @param newFat           nuevo objetivo de grasas en g
     * @param changedAt        instante del cambio
     * @return instancia de {@code MetabolicAdaptationLog} reconstituida
     */
    public static MetabolicAdaptationLog rehydrate(Long id, Long userId, Double previousBMR, Double newBMR,
                                                   Double previousTDEE, Double newTDEE, String reason,
                                                   MetabolicChangeTrigger triggeredBy,
                                                   Double previousCalories, Double newCalories,
                                                   Double previousProtein, Double newProtein,
                                                   Double previousCarbs, Double newCarbs,
                                                   Double previousFat, Double newFat, Instant changedAt) {
        MetabolicAdaptationLog log = new MetabolicAdaptationLog();
        log.id = id;
        log.userId = userId;
        log.previousBMR = previousBMR;
        log.newBMR = newBMR;
        log.previousTDEE = previousTDEE;
        log.newTDEE = newTDEE;
        log.reason = reason;
        log.triggeredBy = triggeredBy;
        log.previousCalories = previousCalories;
        log.newCalories = newCalories;
        log.previousProtein = previousProtein;
        log.newProtein = newProtein;
        log.previousCarbs = previousCarbs;
        log.newCarbs = newCarbs;
        log.previousFat = previousFat;
        log.newFat = newFat;
        log.changedAt = changedAt;
        return log;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Double getPreviousBMR() { return previousBMR; }
    public Double getNewBMR() { return newBMR; }
    public Double getPreviousTDEE() { return previousTDEE; }
    public Double getNewTDEE() { return newTDEE; }
    public String getReason() { return reason; }
    public MetabolicChangeTrigger getTriggeredBy() { return triggeredBy; }
    public Double getPreviousCalories() { return previousCalories; }
    public Double getNewCalories() { return newCalories; }
    public Double getPreviousProtein() { return previousProtein; }
    public Double getNewProtein() { return newProtein; }
    public Double getPreviousCarbs() { return previousCarbs; }
    public Double getNewCarbs() { return newCarbs; }
    public Double getPreviousFat() { return previousFat; }
    public Double getNewFat() { return newFat; }
    public Instant getChangedAt() { return changedAt; }
}
