package com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates;

/**
 * Agregado de dominio que representa un ítem de la despensa personal del usuario.
 * Asocia un alimento concreto a un usuario con la cantidad disponible en gramos,
 * formando la base para la generación de sugerencias de recetas personalizadas.
 */
public class PantryItem {

    private Long id;
    private Long userId;
    private Long foodId;
    private Double quantityG;

    private PantryItem() {}

    /**
     * Crea un nuevo ítem en la despensa del usuario, validando que la cantidad sea positiva.
     *
     * @param userId    identificador del usuario propietario
     * @param foodId    identificador del alimento almacenado
     * @param quantityG cantidad en gramos (debe ser mayor que cero)
     * @return nueva instancia de {@code PantryItem}
     * @throws IllegalArgumentException si {@code quantityG} es nulo o no positivo
     */
    public static PantryItem create(Long userId, Long foodId, Double quantityG) {
        if (quantityG == null || quantityG <= 0) {
            throw new IllegalArgumentException("quantityG must be greater than 0");
        }
        PantryItem item = new PantryItem();
        item.userId = userId;
        item.foodId = foodId;
        item.quantityG = quantityG;
        return item;
    }

    /**
     * Reconstituye un ítem de despensa a partir de datos persistidos.
     *
     * @param id        identificador único persistido
     * @param userId    identificador del usuario propietario
     * @param foodId    identificador del alimento
     * @param quantityG cantidad en gramos
     * @return instancia rehidratada de {@code PantryItem}
     */
    public static PantryItem rehydrate(Long id, Long userId, Long foodId, Double quantityG) {
        PantryItem item = new PantryItem();
        item.id = id;
        item.userId = userId;
        item.foodId = foodId;
        item.quantityG = quantityG;
        return item;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getFoodId() { return foodId; }
    public Double getQuantityG() { return quantityG; }
}
