package com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates;

import java.time.Instant;

/**
 * Agregado de dominio que representa una instantánea de la ubicación geográfica de un usuario.
 * Registra la ciudad y el país detectados en un momento concreto, permitiendo
 * personalizar las recomendaciones nutricionales según el contexto de localización.
 */
public class LocationSnapshot {

    private Long id;
    private Long userId;
    private String city;
    private String country;
    private Instant recordedAt;

    private LocationSnapshot() {}

    /**
     * Crea una nueva instantánea de ubicación para el usuario indicado, registrando el instante actual.
     *
     * @param userId identificador del usuario propietario de la instantánea
     * @param city   nombre de la ciudad detectada
     * @param country país o código de país asociado
     * @return nueva instancia de {@code LocationSnapshot}
     */
    public static LocationSnapshot create(Long userId, String city, String country) {
        LocationSnapshot snapshot = new LocationSnapshot();
        snapshot.userId = userId;
        snapshot.city = city;
        snapshot.country = country;
        snapshot.recordedAt = Instant.now();
        return snapshot;
    }

    /**
     * Reconstituye una instantánea de ubicación a partir de datos persistidos (rehidratación desde la base de datos).
     *
     * @param id         identificador único persistido
     * @param userId     identificador del usuario propietario
     * @param city       nombre de la ciudad
     * @param country    país o código de país
     * @param recordedAt instante en que se registró la ubicación
     * @return instancia rehidratada de {@code LocationSnapshot}
     */
    public static LocationSnapshot rehydrate(Long id, Long userId, String city, String country, Instant recordedAt) {
        LocationSnapshot snapshot = new LocationSnapshot();
        snapshot.id = id;
        snapshot.userId = userId;
        snapshot.city = city;
        snapshot.country = country;
        snapshot.recordedAt = recordedAt;
        return snapshot;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public Instant getRecordedAt() { return recordedAt; }
}
