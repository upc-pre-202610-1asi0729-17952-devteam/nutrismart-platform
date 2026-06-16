package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.BmiCategory;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Agregado raíz que representa el registro de métricas corporales de un usuario.
 * <p>
 * Encapsula el peso, la talla y los objetivos de peso del usuario en un momento dado.
 * Provee lógica de dominio para calcular el IMC y su categoría correspondiente.
 * </p>
 */
public class BodyMetric {

    private Long id;
    private Long userId;
    private Double weightKg;
    private Double heightCm;
    private Instant loggedAt;
    private Double targetWeightKg;
    private LocalDate projectedAchievementDate;

    private BodyMetric() {}

    /**
     * Crea una nueva instancia de {@code BodyMetric} con validación de invariantes de dominio.
     *
     * @param userId                    identificador del usuario propietario del registro
     * @param weightKg                  peso actual en kilogramos (debe ser positivo)
     * @param heightCm                  altura en centímetros (debe ser positiva)
     * @param loggedAt                  marca temporal del registro; si es {@code null} se usa el instante actual
     * @param targetWeightKg            peso objetivo en kilogramos (opcional)
     * @param projectedAchievementDate  fecha estimada para alcanzar el peso objetivo (opcional)
     * @return nueva instancia válida de {@code BodyMetric}
     * @throws IllegalArgumentException si userId es nulo o weightKg/heightCm no son positivos
     */
    public static BodyMetric create(Long userId, Double weightKg, Double heightCm, Instant loggedAt,
                                    Double targetWeightKg, LocalDate projectedAchievementDate) {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (weightKg == null || weightKg <= 0) throw new IllegalArgumentException("weightKg must be positive");
        if (heightCm == null || heightCm <= 0) throw new IllegalArgumentException("heightCm must be positive");
        BodyMetric bm = new BodyMetric();
        bm.userId = userId;
        bm.weightKg = weightKg;
        bm.heightCm = heightCm;
        bm.loggedAt = loggedAt != null ? loggedAt : Instant.now();
        bm.targetWeightKg = targetWeightKg;
        bm.projectedAchievementDate = projectedAchievementDate;
        return bm;
    }

    /**
     * Reconstituye un {@code BodyMetric} desde el almacenamiento de persistencia sin aplicar validaciones de creación.
     *
     * @param id                        identificador persistido
     * @param userId                    identificador del usuario
     * @param weightKg                  peso en kilogramos
     * @param heightCm                  altura en centímetros
     * @param loggedAt                  marca temporal del registro
     * @param targetWeightKg            peso objetivo en kilogramos
     * @param projectedAchievementDate  fecha estimada para alcanzar el objetivo
     * @return instancia de {@code BodyMetric} reconstituida
     */
    public static BodyMetric rehydrate(Long id, Long userId, Double weightKg, Double heightCm, Instant loggedAt,
                                       Double targetWeightKg, LocalDate projectedAchievementDate) {
        BodyMetric bm = new BodyMetric();
        bm.id = id;
        bm.userId = userId;
        bm.weightKg = weightKg;
        bm.heightCm = heightCm;
        bm.loggedAt = loggedAt;
        bm.targetWeightKg = targetWeightKg;
        bm.projectedAchievementDate = projectedAchievementDate;
        return bm;
    }

    /**
     * Actualiza los campos modificables de las métricas corporales.
     * Los valores nulos o no positivos para peso y altura son ignorados.
     *
     * @param weightKg                  nuevo peso en kilogramos (ignorado si es nulo o no positivo)
     * @param heightCm                  nueva altura en centímetros (ignorada si es nula o no positiva)
     * @param targetWeightKg            nuevo peso objetivo (puede ser nulo)
     * @param projectedAchievementDate  nueva fecha estimada de logro (puede ser nula)
     */
    public void update(Double weightKg, Double heightCm, Double targetWeightKg, LocalDate projectedAchievementDate) {
        if (weightKg != null && weightKg > 0) this.weightKg = weightKg;
        if (heightCm != null && heightCm > 0) this.heightCm = heightCm;
        this.targetWeightKg = targetWeightKg;
        this.projectedAchievementDate = projectedAchievementDate;
    }

    /**
     * Calcula el Índice de Masa Corporal (IMC) redondeado a un decimal.
     *
     * @return IMC calculado como peso(kg) / altura(m)²
     */
    public double calculateBmi() {
        return Math.round((weightKg / Math.pow(heightCm / 100.0, 2)) * 10.0) / 10.0;
    }

    /**
     * Determina la categoría de IMC correspondiente al estado actual de las métricas.
     *
     * @return {@link BmiCategory} asociada al IMC calculado
     */
    public BmiCategory getBmiCategory() {
        return BmiCategory.fromBmi(calculateBmi());
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Double getWeightKg() { return weightKg; }
    public Double getHeightCm() { return heightCm; }
    public Instant getLoggedAt() { return loggedAt; }
    public Double getTargetWeightKg() { return targetWeightKg; }
    public LocalDate getProjectedAchievementDate() { return projectedAchievementDate; }
}
