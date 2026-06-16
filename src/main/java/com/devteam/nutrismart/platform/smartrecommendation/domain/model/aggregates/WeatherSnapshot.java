package com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;

import java.time.Instant;

/**
 * Agregado de dominio que representa una instantánea de las condiciones meteorológicas
 * de una ciudad en un momento determinado.
 * Deriva automáticamente el tipo de clima ({@link WeatherType}) a partir de la temperatura
 * registrada y se utiliza para contextualizar las recomendaciones nutricionales.
 */
public class WeatherSnapshot {

    private Long id;
    private String city;
    private String country;
    private Double temperatureCelsius;
    private String condition;
    private WeatherType weatherType;
    private Instant updatedAt;

    private WeatherSnapshot() {}

    /**
     * Deriva el tipo de clima a partir de la temperatura en grados Celsius.
     * HOT si es superior a 28 °C, COLD si es inferior a 12 °C, MILD en caso contrario.
     *
     * @param temperatureCelsius temperatura ambiental en grados Celsius
     * @return tipo de clima derivado
     */
    private static WeatherType deriveWeatherType(Double temperatureCelsius) {
        if (temperatureCelsius > 28) return WeatherType.HOT;
        if (temperatureCelsius < 12) return WeatherType.COLD;
        return WeatherType.MILD;
    }

    /**
     * Crea una nueva instantánea meteorológica derivando el tipo de clima automáticamente.
     *
     * @param city               nombre de la ciudad
     * @param country            código o nombre del país
     * @param temperatureCelsius temperatura ambiental en grados Celsius
     * @param condition          descripción textual de la condición meteorológica
     * @return nueva instancia de {@code WeatherSnapshot}
     */
    public static WeatherSnapshot create(String city, String country, Double temperatureCelsius, String condition) {
        WeatherSnapshot snapshot = new WeatherSnapshot();
        snapshot.city = city;
        snapshot.country = country;
        snapshot.temperatureCelsius = temperatureCelsius;
        snapshot.condition = condition;
        snapshot.weatherType = deriveWeatherType(temperatureCelsius);
        snapshot.updatedAt = Instant.now();
        return snapshot;
    }

    /**
     * Reconstituye una instantánea meteorológica a partir de datos persistidos.
     *
     * @param id                 identificador único persistido
     * @param city               nombre de la ciudad
     * @param country            código o nombre del país
     * @param temperatureCelsius temperatura en grados Celsius
     * @param condition          descripción textual de la condición meteorológica
     * @param weatherType        tipo de clima previamente derivado y almacenado
     * @param updatedAt          instante de la última actualización
     * @return instancia rehidratada de {@code WeatherSnapshot}
     */
    public static WeatherSnapshot rehydrate(Long id, String city, String country, Double temperatureCelsius,
                                            String condition, WeatherType weatherType, Instant updatedAt) {
        WeatherSnapshot snapshot = new WeatherSnapshot();
        snapshot.id = id;
        snapshot.city = city;
        snapshot.country = country;
        snapshot.temperatureCelsius = temperatureCelsius;
        snapshot.condition = condition;
        snapshot.weatherType = weatherType;
        snapshot.updatedAt = updatedAt;
        return snapshot;
    }

    /**
     * Actualiza la instantánea meteorológica con nuevos valores y recalcula el tipo de clima.
     *
     * @param city               nuevo nombre de la ciudad
     * @param country            nuevo código o nombre del país
     * @param temperatureCelsius nueva temperatura en grados Celsius
     * @param condition          nueva descripción textual de la condición meteorológica
     */
    public void update(String city, String country, Double temperatureCelsius, String condition) {
        this.city = city;
        this.country = country;
        this.temperatureCelsius = temperatureCelsius;
        this.condition = condition;
        this.weatherType = deriveWeatherType(temperatureCelsius);
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public Double getTemperatureCelsius() { return temperatureCelsius; }
    public String getCondition() { return condition; }
    public WeatherType getWeatherType() { return weatherType; }
    public Instant getUpdatedAt() { return updatedAt; }
}
