package com.devteam.nutrismart.platform.nutritiontracking.application.commands;

/**
 * Comando para iniciar el análisis visual de un menú mediante una imagen.
 *
 * <p>Representa la intención del usuario de escanear la fotografía de un menú
 * (p. ej. de un restaurante) para que el sistema identifique los platos disponibles
 * y sus posibles valores nutricionales a través de un modelo de visión artificial.</p>
 *
 * @param userId      Identificador del usuario que solicita el escaneo del menú.
 * @param imageBase64 Imagen del menú codificada en formato Base64.
 */
public record ScanMenuCommand(Long userId, String imageBase64) {}
