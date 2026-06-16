package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

/**
 * DTO que representa un alimento detectado en una imagen de plato.
 * <p>
 * Contiene el nombre del alimento identificado por el modelo de reconocimiento visual
 * y la cantidad estimada en gramos para ese alimento dentro del plato analizado.
 * </p>
 *
 * @param name               nombre del alimento detectado (en inglés, tal como lo devuelve el modelo de IA).
 * @param estimatedQuantityG cantidad estimada del alimento en gramos.
 */
public record DetectedFoodItem(String name, int estimatedQuantityG) {}
