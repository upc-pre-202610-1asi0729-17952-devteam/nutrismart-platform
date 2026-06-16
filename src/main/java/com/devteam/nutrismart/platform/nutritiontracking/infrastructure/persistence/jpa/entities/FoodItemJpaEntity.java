package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

/**
 * Entidad JPA que representa un alimento dentro del catálogo nutricional de la plataforma.
 * <p>
 * Almacena la información nutricional detallada de cada alimento por cada 100 gramos,
 * así como metadatos de origen, restricciones alimentarias y compatibilidad climática.
 * Mapea a la tabla {@code foods} en la base de datos.
 * </p>
 */
@Entity
@Table(name = "foods")
@Getter
@Setter
@NoArgsConstructor
public class FoodItemJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Nombre del alimento en inglés")
    private String name;

    @Comment("Nombre del alimento en español")
    private String nameEs;

    @Comment("Fuente u origen del dato nutricional del alimento")
    private String source;

    @Comment("Tamaño de la porción de referencia del alimento")
    private Double servingSize;

    @Comment("Unidad de medida de la porción de referencia (gramos, mililitros, etc.)")
    private String servingUnit;

    @Comment("Cantidad de calorías por cada 100 gramos del alimento")
    private Double caloriesPer100g;

    @Comment("Cantidad de proteína en gramos por cada 100 gramos del alimento")
    private Double proteinPer100g;

    @Comment("Cantidad de carbohidratos en gramos por cada 100 gramos del alimento")
    private Double carbsPer100g;

    @Comment("Cantidad de grasa en gramos por cada 100 gramos del alimento")
    private Double fatPer100g;

    @Comment("Cantidad de fibra dietética en gramos por cada 100 gramos del alimento")
    private Double fiberPer100g;

    @Comment("Cantidad de azúcar en gramos por cada 100 gramos del alimento")
    private Double sugarPer100g;

    @Comment("Lista de restricciones alimentarias asociadas al alimento, almacenadas como texto separado por comas")
    @Column(columnDefinition = "TEXT")
    private String restrictions;

    @Comment("Clave única normalizada del nombre del alimento, utilizada para búsquedas y deduplicación")
    @Column(unique = true)
    private String nameKey;

    @Comment("Categoría a la que pertenece el alimento (ej. fruta, verdura, proteína)")
    private String category;

    @Comment("Tipo de ítem alimentario (ej. comida, bebida, suplemento)")
    private String itemType;

    @Comment("Tipos de clima para los cuales el alimento es recomendado, almacenados como texto separado por comas")
    @Column(columnDefinition = "TEXT")
    private String weatherTypes;

    @Comment("Ciudad de origen del alimento")
    private String originCity;

    @Comment("País de origen del alimento")
    private String originCountry;
}
