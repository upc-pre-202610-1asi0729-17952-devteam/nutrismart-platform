package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates;

import java.time.Instant;

/**
 * Agregado raíz que representa la composición corporal de un usuario en un momento dado.
 * <p>
 * Almacena medidas antropométricas y provee lógica de dominio para estimar el porcentaje
 * de grasa corporal utilizando la fórmula de la Marina de los EE. UU. (U.S. Navy Method).
 * Si se provee un valor de sobreescritura ({@code overrideBodyFatPercent}), este tiene precedencia.
 * </p>
 */
public class BodyComposition {

    private Long id;
    private Long userId;
    private Double waistCm;
    private Double neckCm;
    private Double heightCm;
    private Double weightKg;
    private Instant measuredAt;
    private Double previousBodyFatPercent;
    private Double overrideBodyFatPercent;

    private BodyComposition() {}

    /**
     * Crea una nueva instancia de {@code BodyComposition} con validación de invariantes de dominio.
     *
     * @param userId                  identificador del usuario
     * @param waistCm                 circunferencia de cintura en centímetros
     * @param neckCm                  circunferencia de cuello en centímetros
     * @param heightCm                altura en centímetros
     * @param weightKg                peso en kilogramos
     * @param measuredAt              instante de medición; si es {@code null} se usa el instante actual
     * @param previousBodyFatPercent  porcentaje de grasa corporal anterior (referencia histórica)
     * @param overrideBodyFatPercent  valor manual que reemplaza el cálculo por fórmula (opcional)
     * @return nueva instancia válida de {@code BodyComposition}
     * @throws IllegalArgumentException si userId es nulo
     */
    public static BodyComposition create(Long userId, Double waistCm, Double neckCm, Double heightCm, Double weightKg,
                                         Instant measuredAt, Double previousBodyFatPercent, Double overrideBodyFatPercent) {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        BodyComposition bc = new BodyComposition();
        bc.userId = userId;
        bc.waistCm = waistCm;
        bc.neckCm = neckCm;
        bc.heightCm = heightCm;
        bc.weightKg = weightKg;
        bc.measuredAt = measuredAt != null ? measuredAt : Instant.now();
        bc.previousBodyFatPercent = previousBodyFatPercent;
        bc.overrideBodyFatPercent = overrideBodyFatPercent;
        return bc;
    }

    /**
     * Reconstituye un {@code BodyComposition} desde el almacenamiento de persistencia.
     *
     * @param id                      identificador persistido
     * @param userId                  identificador del usuario
     * @param waistCm                 circunferencia de cintura en centímetros
     * @param neckCm                  circunferencia de cuello en centímetros
     * @param heightCm                altura en centímetros
     * @param weightKg                peso en kilogramos
     * @param measuredAt              instante de medición
     * @param previousBodyFatPercent  porcentaje de grasa corporal anterior
     * @param overrideBodyFatPercent  valor manual de grasa corporal (opcional)
     * @return instancia de {@code BodyComposition} reconstituida
     */
    public static BodyComposition rehydrate(Long id, Long userId, Double waistCm, Double neckCm, Double heightCm,
                                            Double weightKg, Instant measuredAt, Double previousBodyFatPercent,
                                            Double overrideBodyFatPercent) {
        BodyComposition bc = new BodyComposition();
        bc.id = id;
        bc.userId = userId;
        bc.waistCm = waistCm;
        bc.neckCm = neckCm;
        bc.heightCm = heightCm;
        bc.weightKg = weightKg;
        bc.measuredAt = measuredAt;
        bc.previousBodyFatPercent = previousBodyFatPercent;
        bc.overrideBodyFatPercent = overrideBodyFatPercent;
        return bc;
    }

    /**
     * Actualiza las medidas modificables de la composición corporal.
     *
     * @param waistCm                nueva circunferencia de cintura en cm
     * @param neckCm                 nueva circunferencia de cuello en cm
     * @param heightCm               nueva altura en cm
     * @param weightKg               nuevo peso en kg
     * @param overrideBodyFatPercent nuevo valor manual de grasa corporal (puede ser nulo)
     */
    public void update(Double waistCm, Double neckCm, Double heightCm, Double weightKg, Double overrideBodyFatPercent) {
        this.waistCm = waistCm;
        this.neckCm = neckCm;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.overrideBodyFatPercent = overrideBodyFatPercent;
    }

    /**
     * Calcula el porcentaje estimado de grasa corporal.
     * Si existe un valor de sobreescritura ({@code overrideBodyFatPercent}), este se retorna directamente.
     * En caso contrario se aplica la fórmula de la Marina de los EE. UU.
     *
     * @return porcentaje de grasa corporal estimado o sobreescrito
     */
    public double calculateBodyFatPercent() {
        if (overrideBodyFatPercent != null) return overrideBodyFatPercent;
        return 495.0 / (1.0324 - 0.19077 * Math.log10(waistCm - neckCm) + 0.15456 * Math.log10(heightCm)) - 450.0;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Double getWaistCm() { return waistCm; }
    public Double getNeckCm() { return neckCm; }
    public Double getHeightCm() { return heightCm; }
    public Double getWeightKg() { return weightKg; }
    public Instant getMeasuredAt() { return measuredAt; }
    public Double getPreviousBodyFatPercent() { return previousBodyFatPercent; }
    public Double getOverrideBodyFatPercent() { return overrideBodyFatPercent; }
}
