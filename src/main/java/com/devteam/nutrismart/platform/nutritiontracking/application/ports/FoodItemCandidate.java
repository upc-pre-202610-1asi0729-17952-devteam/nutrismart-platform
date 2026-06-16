package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

/**
 * DTO que representa un alimento candidato recuperado del catálogo interno del sistema.
 * <p>
 * Se utiliza para llevar los datos mínimos de un ítem de alimento existente en la base de datos
 * durante los procesos de emparejamiento (matching) y clasificación de platos o menús.
 * </p>
 *
 * @param id      identificador único del alimento en el sistema.
 * @param nameKey clave normalizada del alimento, usada para búsquedas y comparaciones internas.
 * @param name    nombre del alimento en inglés.
 * @param nameEs  nombre del alimento en español.
 */
public record FoodItemCandidate(Long id, String nameKey, String name, String nameEs) {}
