package com.devteam.nutrismart.platform.nutritiontracking.application.commands;

/**
 * @deprecated Reemplazado por {@code ScanMenuPhotoCommand} en el BC restaurantintelligence.
 * Pendiente de eliminar tras confirmar que el frontend apunta al nuevo endpoint.
 */
@Deprecated(forRemoval = true)
public record ScanMenuCommand(Long userId, String imageBase64) {}
