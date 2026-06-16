package com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates;

import java.time.Instant;

/**
 * Agregado de dominio que representa el contexto de viaje de un usuario.
 * Cuando un usuario se encuentra en una ciudad distinta a la habitual, este contexto
 * permite personalizar las tarjetas de recomendación con alimentos y experiencias
 * típicas del destino visitado.
 */
public class TravelContext {

    private Long id;
    private Long userId;
    private String city;
    private String country;
    private Boolean isActive;
    private Boolean isManual;
    private Instant activatedAt;

    private TravelContext() {}

    /**
     * Crea un nuevo contexto de viaje en estado inactivo para el usuario indicado.
     *
     * @param userId   identificador del usuario
     * @param city     ciudad de destino del viaje
     * @param country  país de destino del viaje
     * @param isManual {@code true} si el usuario configuró el contexto manualmente
     * @return nueva instancia inactiva de {@code TravelContext}
     */
    public static TravelContext create(Long userId, String city, String country, Boolean isManual) {
        TravelContext context = new TravelContext();
        context.userId = userId;
        context.city = city;
        context.country = country;
        context.isManual = isManual;
        context.isActive = false;
        context.activatedAt = null;
        return context;
    }

    /**
     * Reconstituye un contexto de viaje a partir de datos persistidos.
     *
     * @param id          identificador único persistido
     * @param userId      identificador del usuario
     * @param city        ciudad de destino
     * @param country     país de destino
     * @param isActive    indica si el contexto de viaje está actualmente activo
     * @param isManual    indica si fue configurado manualmente por el usuario
     * @param activatedAt instante en que se activó el contexto (puede ser nulo)
     * @return instancia rehidratada de {@code TravelContext}
     */
    public static TravelContext rehydrate(Long id, Long userId, String city, String country,
                                          Boolean isActive, Boolean isManual, Instant activatedAt) {
        TravelContext context = new TravelContext();
        context.id = id;
        context.userId = userId;
        context.city = city;
        context.country = country;
        context.isActive = isActive;
        context.isManual = isManual;
        context.activatedAt = activatedAt;
        return context;
    }

    /**
     * Actualiza los datos mutables del contexto de viaje.
     *
     * @param isActive    nuevo estado de activación del contexto
     * @param city        nueva ciudad de destino
     * @param country     nuevo país de destino
     * @param activatedAt nuevo instante de activación (puede ser nulo)
     */
    public void update(Boolean isActive, String city, String country, Instant activatedAt) {
        this.isActive = isActive;
        this.city = city;
        this.country = country;
        this.activatedAt = activatedAt;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public Boolean getIsActive() { return isActive; }
    public Boolean getIsManual() { return isManual; }
    public Instant getActivatedAt() { return activatedAt; }
}
