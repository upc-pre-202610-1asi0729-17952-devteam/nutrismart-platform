package com.devteam.nutrismart.platform.nutritiontracking.application.ports;

import java.util.List;

/**
 * @deprecated Reemplazado por {@link PlateImageRecognitionPort} (plate scan, nutritiontracking BC)
 * y {@code MenuImageRecognitionPort} (menu scan, restaurantintelligence BC).
 * Este interface será eliminado en la próxima limpieza.
 */
@Deprecated(forRemoval = true)
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
