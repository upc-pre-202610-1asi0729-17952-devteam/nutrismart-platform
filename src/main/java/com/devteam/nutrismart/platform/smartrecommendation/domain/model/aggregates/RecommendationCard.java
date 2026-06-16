package com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;

import java.util.Set;

/**
 * Agregado de dominio que representa una tarjeta de recomendación nutricional personalizada.
 * Cada tarjeta asocia un alimento a un contexto específico (clima, viaje, prevención o intervención)
 * y contiene descripciones bilingües para mostrar al usuario.
 * Los tipos válidos son: {@code weather}, {@code travel}, {@code preventive} e {@code intervention}.
 */
public class RecommendationCard {

    private static final Set<String> VALID_CARD_TYPES = Set.of("weather", "travel", "preventive", "intervention");

    private Long id;
    private String badge;
    private WeatherType weatherType;
    private String travelCity;
    private String cardType;
    private Long foodId;
    private String description;
    private String descriptionEs;

    private RecommendationCard() {}

    /**
     * Crea una nueva tarjeta de recomendación validando el tipo de tarjeta y la presencia del alimento.
     *
     * @param badge         etiqueta visual de la tarjeta (p. ej. {@code seasonal}, {@code local})
     * @param weatherType   tipo de clima asociado (puede ser nulo si no aplica)
     * @param travelCity    ciudad de viaje asociada (puede ser nula si no aplica)
     * @param cardType      tipo de tarjeta; debe pertenecer a los valores válidos del dominio
     * @param foodId        identificador del alimento recomendado (no puede ser nulo)
     * @param description   descripción de la recomendación en inglés
     * @param descriptionEs descripción de la recomendación en español
     * @return nueva instancia de {@code RecommendationCard}
     * @throws IllegalArgumentException si {@code cardType} es inválido o {@code foodId} es nulo
     */
    public static RecommendationCard create(String badge, WeatherType weatherType, String travelCity,
                                            String cardType, Long foodId, String description,
                                            String descriptionEs) {
        if (cardType == null || !VALID_CARD_TYPES.contains(cardType)) {
            throw new IllegalArgumentException("cardType must be one of: " + VALID_CARD_TYPES);
        }
        if (foodId == null) {
            throw new IllegalArgumentException("foodId must not be null");
        }
        RecommendationCard card = new RecommendationCard();
        card.badge = badge;
        card.weatherType = weatherType;
        card.travelCity = travelCity;
        card.cardType = cardType;
        card.foodId = foodId;
        card.description = description;
        card.descriptionEs = descriptionEs;
        return card;
    }

    /**
     * Reconstituye una tarjeta de recomendación a partir de datos persistidos.
     *
     * @param id            identificador único persistido
     * @param badge         etiqueta visual de la tarjeta
     * @param weatherType   tipo de clima asociado
     * @param travelCity    ciudad de viaje asociada
     * @param cardType      tipo de tarjeta
     * @param foodId        identificador del alimento recomendado
     * @param description   descripción en inglés
     * @param descriptionEs descripción en español
     * @return instancia rehidratada de {@code RecommendationCard}
     */
    public static RecommendationCard rehydrate(Long id, String badge, WeatherType weatherType,
                                               String travelCity, String cardType, Long foodId,
                                               String description, String descriptionEs) {
        RecommendationCard card = new RecommendationCard();
        card.id = id;
        card.badge = badge;
        card.weatherType = weatherType;
        card.travelCity = travelCity;
        card.cardType = cardType;
        card.foodId = foodId;
        card.description = description;
        card.descriptionEs = descriptionEs;
        return card;
    }

    public Long getId() { return id; }
    public String getBadge() { return badge; }
    public WeatherType getWeatherType() { return weatherType; }
    public String getTravelCity() { return travelCity; }
    public String getCardType() { return cardType; }
    public Long getFoodId() { return foodId; }
    public String getDescription() { return description; }
    public String getDescriptionEs() { return descriptionEs; }
}
