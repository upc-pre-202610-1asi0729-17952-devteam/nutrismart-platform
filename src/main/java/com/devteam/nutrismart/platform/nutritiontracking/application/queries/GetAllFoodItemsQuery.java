package com.devteam.nutrismart.platform.nutritiontracking.application.queries;

import java.util.List;

/**
 * Consulta para obtener todos los ítems alimenticios del catálogo, con filtrado opcional por restricciones.
 *
 * <p>Permite recuperar la lista de alimentos disponibles en el sistema, pudiendo
 * filtrarlos según las restricciones alimentarias del usuario (p. ej. sin gluten,
 * vegano, sin lactosa). Si la lista de restricciones está vacía o es nula,
 * se devuelven todos los alimentos sin filtrar.</p>
 *
 * @param restrictions Lista de restricciones alimentarias por las que se desea filtrar el catálogo.
 */
public record GetAllFoodItemsQuery(List<String> restrictions) {}
