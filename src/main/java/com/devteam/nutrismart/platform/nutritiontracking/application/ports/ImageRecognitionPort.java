package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

import java.util.List;

/**
 * Puerto de salida para el reconocimiento de imágenes relacionadas con alimentos.
 * <p>
 * Define el contrato que deben cumplir los adaptadores que se integran con servicios
 * externos de visión artificial (p. ej., modelos multimodales de IA) para analizar
 * imágenes de platos o menús de restaurante y extraer información alimentaria de ellas.
 * </p>
 */
public interface ImageRecognitionPort {

    /**
     * Identifica los alimentos presentes en la imagen de un plato y estima sus cantidades.
     *
     * @param imageBase64 imagen del plato codificada en Base64.
     * @return lista de {@link DetectedFoodItem} con los alimentos detectados y sus cantidades
     *         estimadas en gramos; puede ser vacía si no se detecta ningún alimento.
     */
    List<DetectedFoodItem> identifyPlateContents(String imageBase64);

    /**
     * Extrae los platos o ítems presentes en la imagen de un menú de restaurante.
     *
     * @param imageBase64 imagen del menú codificada en Base64.
     * @return lista de {@link MenuDishCandidate} con los platos encontrados en el menú
     *         junto a su precio cuando es legible; puede ser vacía si no se extrae ningún ítem.
     */
    List<MenuDishCandidate> extractMenuItems(String imageBase64);
}
