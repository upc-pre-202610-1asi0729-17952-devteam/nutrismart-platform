package com.devteam.nutrismart.platform.nutritiontracking.domain.model.aggregates;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.exceptions.DomainInvalidExceptions;
import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.FoodRestriction;

import java.util.List;

/**
 * Agregado raíz que representa un alimento dentro del catálogo nutricional.
 * <p>
 * Contiene la información nutricional por cada 100 g del alimento (calorías,
 * proteínas, carbohidratos, grasas, fibra y azúcar), así como metadatos como
 * restricciones dietéticas, tipo de ítem, tipos de clima asociados y origen geográfico.
 * La instanciación se realiza exclusivamente mediante los métodos de fábrica estáticos
 * para garantizar la integridad de los invariantes del dominio.
 * </p>
 */
public class FoodItem {

    private Long id;
    private String name;
    private String nameEs;
    private String source;
    private Double servingSize;
    private String servingUnit;
    private Double caloriesPer100g;
    private Double proteinPer100g;
    private Double carbsPer100g;
    private Double fatPer100g;
    private Double fiberPer100g;
    private Double sugarPer100g;
    private List<FoodRestriction> restrictions;
    private String nameKey;
    private String category;
    private String itemType;
    private List<String> weatherTypes;
    private String originCity;
    private String originCountry;

    private FoodItem() {}

    /**
     * Crea una nueva instancia de {@code FoodItem} aplicando las reglas de negocio.
     * <p>
     * Valida que el nombre del alimento no esté vacío y que las calorías por 100 g
     * sean un valor no negativo antes de construir el agregado.
     * </p>
     *
     * @param name            nombre del alimento en inglés; no puede ser {@code null} ni estar en blanco.
     * @param nameEs          nombre del alimento en español.
     * @param source          fuente o proveedor del alimento.
     * @param servingSize     tamaño de la porción de referencia.
     * @param servingUnit     unidad de medida de la porción (p. ej. "g", "ml").
     * @param caloriesPer100g calorías por cada 100 g del alimento; debe ser mayor o igual a cero.
     * @param proteinPer100g  gramos de proteína por cada 100 g del alimento.
     * @param carbsPer100g    gramos de carbohidratos por cada 100 g del alimento.
     * @param fatPer100g      gramos de grasa por cada 100 g del alimento.
     * @param fiberPer100g    gramos de fibra por cada 100 g del alimento.
     * @param sugarPer100g    gramos de azúcar por cada 100 g del alimento.
     * @param restrictions    lista de restricciones dietéticas aplicables al alimento.
     * @param nameKey         clave normalizada única del alimento utilizada en búsquedas.
     * @param category        categoría a la que pertenece el alimento.
     * @param itemType        tipo de ítem (p. ej. "food", "recipe").
     * @param weatherTypes    lista de tipos de clima en los que se recomienda el consumo del alimento.
     * @param originCity      ciudad de origen del alimento.
     * @param originCountry   país de origen del alimento.
     * @return nueva instancia de {@code FoodItem} lista para ser persistida.
     * @throws DomainInvalidExceptions si {@code name} está en blanco o si {@code caloriesPer100g}
     *                                 es {@code null} o negativo.
     */
    public static FoodItem create(String name, String nameEs, String source, Double servingSize, String servingUnit,
                                  Double caloriesPer100g, Double proteinPer100g, Double carbsPer100g,
                                  Double fatPer100g, Double fiberPer100g, Double sugarPer100g,
                                  List<FoodRestriction> restrictions, String nameKey, String category,
                                  String itemType, List<String> weatherTypes, String originCity, String originCountry) {
        if (name == null || name.isBlank()) throw new DomainInvalidExceptions("name must not be blank");
        if (caloriesPer100g == null || caloriesPer100g < 0) throw new DomainInvalidExceptions("caloriesPer100g must be >= 0");

        FoodItem item = new FoodItem();
        item.name = name;
        item.nameEs = nameEs;
        item.source = source;
        item.servingSize = servingSize;
        item.servingUnit = servingUnit;
        item.caloriesPer100g = caloriesPer100g;
        item.proteinPer100g = proteinPer100g;
        item.carbsPer100g = carbsPer100g;
        item.fatPer100g = fatPer100g;
        item.fiberPer100g = fiberPer100g;
        item.sugarPer100g = sugarPer100g;
        item.restrictions = restrictions;
        item.nameKey = nameKey;
        item.category = category;
        item.itemType = itemType;
        item.weatherTypes = weatherTypes;
        item.originCity = originCity;
        item.originCountry = originCountry;
        return item;
    }

    /**
     * Reconstituye una instancia de {@code FoodItem} a partir de datos persistidos.
     * <p>
     * Este método omite las validaciones de negocio, ya que los datos provienen de un
     * estado previamente válido almacenado en la capa de persistencia.
     * </p>
     *
     * @param id              identificador único del alimento almacenado.
     * @param name            nombre del alimento en inglés.
     * @param nameEs          nombre del alimento en español.
     * @param source          fuente o proveedor del alimento.
     * @param servingSize     tamaño de la porción de referencia.
     * @param servingUnit     unidad de medida de la porción.
     * @param caloriesPer100g calorías por cada 100 g del alimento.
     * @param proteinPer100g  gramos de proteína por cada 100 g del alimento.
     * @param carbsPer100g    gramos de carbohidratos por cada 100 g del alimento.
     * @param fatPer100g      gramos de grasa por cada 100 g del alimento.
     * @param fiberPer100g    gramos de fibra por cada 100 g del alimento.
     * @param sugarPer100g    gramos de azúcar por cada 100 g del alimento.
     * @param restrictions    lista de restricciones dietéticas aplicables al alimento.
     * @param nameKey         clave normalizada única del alimento.
     * @param category        categoría del alimento.
     * @param itemType        tipo de ítem.
     * @param weatherTypes    tipos de clima en los que se recomienda el alimento.
     * @param originCity      ciudad de origen del alimento.
     * @param originCountry   país de origen del alimento.
     * @return instancia de {@code FoodItem} reconstituida con el estado persistido.
     */
    public static FoodItem rehydrate(Long id, String name, String nameEs, String source, Double servingSize,
                                     String servingUnit, Double caloriesPer100g, Double proteinPer100g,
                                     Double carbsPer100g, Double fatPer100g, Double fiberPer100g,
                                     Double sugarPer100g, List<FoodRestriction> restrictions, String nameKey,
                                     String category, String itemType, List<String> weatherTypes,
                                     String originCity, String originCountry) {
        FoodItem item = new FoodItem();
        item.id = id;
        item.name = name;
        item.nameEs = nameEs;
        item.source = source;
        item.servingSize = servingSize;
        item.servingUnit = servingUnit;
        item.caloriesPer100g = caloriesPer100g;
        item.proteinPer100g = proteinPer100g;
        item.carbsPer100g = carbsPer100g;
        item.fatPer100g = fatPer100g;
        item.fiberPer100g = fiberPer100g;
        item.sugarPer100g = sugarPer100g;
        item.restrictions = restrictions;
        item.nameKey = nameKey;
        item.category = category;
        item.itemType = itemType;
        item.weatherTypes = weatherTypes;
        item.originCity = originCity;
        item.originCountry = originCountry;
        return item;
    }

    /**
     * Actualiza todos los atributos del alimento con los valores proporcionados.
     * <p>
     * Reemplaza la totalidad de los campos mutables sin alterar el identificador
     * único del agregado.
     * </p>
     *
     * @param name            nuevo nombre del alimento en inglés.
     * @param nameEs          nuevo nombre del alimento en español.
     * @param source          nueva fuente o proveedor del alimento.
     * @param servingSize     nuevo tamaño de la porción de referencia.
     * @param servingUnit     nueva unidad de medida de la porción.
     * @param caloriesPer100g nuevas calorías por cada 100 g del alimento.
     * @param proteinPer100g  nuevos gramos de proteína por cada 100 g del alimento.
     * @param carbsPer100g    nuevos gramos de carbohidratos por cada 100 g del alimento.
     * @param fatPer100g      nuevos gramos de grasa por cada 100 g del alimento.
     * @param fiberPer100g    nuevos gramos de fibra por cada 100 g del alimento.
     * @param sugarPer100g    nuevos gramos de azúcar por cada 100 g del alimento.
     * @param restrictions    nueva lista de restricciones dietéticas.
     * @param nameKey         nueva clave normalizada única del alimento.
     * @param category        nueva categoría del alimento.
     * @param itemType        nuevo tipo de ítem.
     * @param weatherTypes    nuevos tipos de clima asociados al alimento.
     * @param originCity      nueva ciudad de origen del alimento.
     * @param originCountry   nuevo país de origen del alimento.
     */
    public void update(String name, String nameEs, String source, Double servingSize, String servingUnit,
                       Double caloriesPer100g, Double proteinPer100g, Double carbsPer100g,
                       Double fatPer100g, Double fiberPer100g, Double sugarPer100g,
                       List<FoodRestriction> restrictions, String nameKey, String category,
                       String itemType, List<String> weatherTypes, String originCity, String originCountry) {
        this.name = name;
        this.nameEs = nameEs;
        this.source = source;
        this.servingSize = servingSize;
        this.servingUnit = servingUnit;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100g = proteinPer100g;
        this.carbsPer100g = carbsPer100g;
        this.fatPer100g = fatPer100g;
        this.fiberPer100g = fiberPer100g;
        this.sugarPer100g = sugarPer100g;
        this.restrictions = restrictions;
        this.nameKey = nameKey;
        this.category = category;
        this.itemType = itemType;
        this.weatherTypes = weatherTypes;
        this.originCity = originCity;
        this.originCountry = originCountry;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getNameEs() { return nameEs; }
    public String getSource() { return source; }
    public Double getServingSize() { return servingSize; }
    public String getServingUnit() { return servingUnit; }
    public Double getCaloriesPer100g() { return caloriesPer100g; }
    public Double getProteinPer100g() { return proteinPer100g; }
    public Double getCarbsPer100g() { return carbsPer100g; }
    public Double getFatPer100g() { return fatPer100g; }
    public Double getFiberPer100g() { return fiberPer100g; }
    public Double getSugarPer100g() { return sugarPer100g; }
    public List<FoodRestriction> getRestrictions() { return restrictions; }
    public String getNameKey() { return nameKey; }
    public String getCategory() { return category; }
    public String getItemType() { return itemType; }
    public List<String> getWeatherTypes() { return weatherTypes; }
    public String getOriginCity() { return originCity; }
    public String getOriginCountry() { return originCountry; }
}
