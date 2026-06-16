package com.devteam.nutrismart.platform.nutritiontracking.application.commands;

/**
 * Comando para iniciar el análisis visual de un plato de comida mediante una imagen.
 *
 * <p>Representa la intención del usuario de escanear la fotografía de un plato para
 * que el sistema identifique los alimentos presentes y estime sus valores nutricionales
 * utilizando un modelo de visión artificial.</p>
 *
 * @param userId      Identificador del usuario que solicita el escaneo del plato.
 * @param imageBase64 Imagen del plato codificada en formato Base64.
 * @param mealType    Tipo de comida al que pertenece el plato (p. ej. desayuno, almuerzo, cena).
 */
public record ScanPlateCommand(Long userId, String imageBase64, String mealType) {}
