package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.nutritiontracking.domain.model.valueobjects.MealType;
import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.Instant;

/**
 * Entidad JPA que representa el registro de una comida consumida por un usuario.
 * <p>
 * Persiste la información nutricional detallada de cada ingesta registrada,
 * incluyendo el alimento consumido, la cantidad, el tipo de comida y los
 * macronutrientes calculados. Mapea a la tabla {@code nutrition_log} en la base de datos.
 * </p>
 */
@Entity
@Table(name = "nutrition_log")
@Getter
@Setter
@NoArgsConstructor
public class MealRecordJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Identificador del usuario que realizó el registro de la comida")
    @Column(nullable = false)
    private Long userId;

    @Comment("Identificador del alimento consumido, referencia al catálogo de alimentos")
    private Long foodId;

    @Comment("Nombre del alimento en inglés en el momento del registro")
    private String foodItemName;

    @Comment("Nombre del alimento en español en el momento del registro")
    private String foodItemNameEs;

    @Comment("Tipo de comida del registro (desayuno, almuerzo, cena, merienda, etc.)")
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @Comment("Cantidad del alimento consumido expresada en la unidad indicada")
    private Double quantity;

    @Comment("Unidad de medida de la cantidad consumida (gramos, mililitros, etc.)")
    private String unit;

    @Comment("Calorías totales aportadas por la porción consumida")
    private Double calories;

    @Comment("Proteína total en gramos aportada por la porción consumida")
    private Double protein;

    @Comment("Carbohidratos totales en gramos aportados por la porción consumida")
    private Double carbs;

    @Comment("Grasa total en gramos aportada por la porción consumida")
    private Double fat;

    @Comment("Fibra dietética total en gramos aportada por la porción consumida")
    private Double fiber;

    @Comment("Azúcar total en gramos aportada por la porción consumida")
    private Double sugar;

    @Comment("Marca de tiempo exacta en la que se registró la ingesta de la comida")
    private Instant loggedAt;
}
