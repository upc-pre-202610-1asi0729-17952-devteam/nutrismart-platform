package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

/**
 * DTO que representa un plato candidato extraído de la imagen de un menú de restaurante.
 * <p>
 * Contiene el nombre del plato tal como fue leído en el menú y su precio cuando este
 * es legible en la imagen. Se utiliza como entrada para el proceso de clasificación
 * y emparejamiento de platos con el catálogo interno de alimentos.
 * </p>
 *
 * @param name  nombre del plato extraído del menú.
 * @param price precio del plato tal como aparece en el menú; puede ser {@code null} si no es legible.
 */
public record MenuDishCandidate(String name, String price) {}
