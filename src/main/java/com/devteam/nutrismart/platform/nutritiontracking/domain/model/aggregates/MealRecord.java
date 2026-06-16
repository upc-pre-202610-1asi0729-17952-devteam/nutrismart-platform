package com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.exceptions.DomainInvalidExceptions;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.MealType;

import java.time.Instant;

/**
 * Agregado raíz que representa el registro de un alimento consumido por un usuario
 * en una comida específica.
 * <p>
 * Encapsula los datos nutricionales correspondientes a la cantidad consumida del alimento
 * (calorías, proteínas, carbohidratos, grasas, fibra y azúcar), el tipo de comida
 * y la marca de tiempo en que se registró el consumo. La instanciación se realiza
 * exclusivamente mediante los métodos de fábrica estáticos para garantizar la
 * integridad de los invariantes del dominio.
 * </p>
 */
public class MealRecord {

    private Long id;
    private Long userId;
    private Long foodId;
    private String foodItemName;
    private String foodItemNameEs;
    private MealType mealType;
    private Double quantity;
    private String unit;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;
    private Double fiber;
    private Double sugar;
    private Instant loggedAt;

    private MealRecord() {}

    public static MealRecord create(Long userId, Long foodId, String foodItemName, String foodItemNameEs,
                                    MealType mealType, Double quantity, String unit,
                                    Double calories, Double protein, Double carbs,
                                    Double fat, Double fiber, Double sugar) {
        if (userId == null) throw new DomainInvalidExceptions("userId must not be null");
        if (mealType == null) throw new DomainInvalidExceptions("mealType must not be null");
        if (quantity == null || quantity <= 0) throw new DomainInvalidExceptions("quantity must be greater than 0");

        MealRecord record = new MealRecord();
        record.userId = userId;
        record.foodId = foodId;
        record.foodItemName = foodItemName;
        record.foodItemNameEs = foodItemNameEs;
        record.mealType = mealType;
        record.quantity = quantity;
        record.unit = unit;
        record.calories = calories;
        record.protein = protein;
        record.carbs = carbs;
        record.fat = fat;
        record.fiber = fiber;
        record.sugar = sugar;
        record.loggedAt = Instant.now();
        return record;
    }

    public static MealRecord rehydrate(Long id, Long userId, Long foodId, String foodItemName, String foodItemNameEs,
                                       MealType mealType, Double quantity, String unit,
                                       Double calories, Double protein, Double carbs,
                                       Double fat, Double fiber, Double sugar, Instant loggedAt) {
        MealRecord record = new MealRecord();
        record.id = id;
        record.userId = userId;
        record.foodId = foodId;
        record.foodItemName = foodItemName;
        record.foodItemNameEs = foodItemNameEs;
        record.mealType = mealType;
        record.quantity = quantity;
        record.unit = unit;
        record.calories = calories;
        record.protein = protein;
        record.carbs = carbs;
        record.fat = fat;
        record.fiber = fiber;
        record.sugar = sugar;
        record.loggedAt = loggedAt;
        return record;
    }

    public void update(String foodItemName, String foodItemNameEs, MealType mealType, Double quantity, String unit,
                       Double calories, Double protein, Double carbs, Double fat, Double fiber, Double sugar) {
        this.foodItemName = foodItemName;
        this.foodItemNameEs = foodItemNameEs;
        this.mealType = mealType;
        this.quantity = quantity;
        this.unit = unit;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fiber = fiber;
        this.sugar = sugar;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getFoodId() { return foodId; }
    public String getFoodItemName() { return foodItemName; }
    public String getFoodItemNameEs() { return foodItemNameEs; }
    public MealType getMealType() { return mealType; }
    public Double getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public Double getCalories() { return calories; }
    public Double getProtein() { return protein; }
    public Double getCarbs() { return carbs; }
    public Double getFat() { return fat; }
    public Double getFiber() { return fiber; }
    public Double getSugar() { return sugar; }
    public Instant getLoggedAt() { return loggedAt; }
}
