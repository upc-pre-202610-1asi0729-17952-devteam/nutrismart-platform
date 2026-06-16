package com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects;

/**
 * Tipo de clima derivado de la temperatura ambiental.
 * Se utiliza para clasificar condiciones meteorológicas y personalizar las recomendaciones nutricionales.
 */
public enum WeatherType {
    /** Clima caluroso: temperatura superior a 28 °C. */
    HOT,
    /** Clima frío: temperatura inferior a 12 °C. */
    COLD,
    /** Clima templado: temperatura entre 12 °C y 28 °C. */
    MILD
}
