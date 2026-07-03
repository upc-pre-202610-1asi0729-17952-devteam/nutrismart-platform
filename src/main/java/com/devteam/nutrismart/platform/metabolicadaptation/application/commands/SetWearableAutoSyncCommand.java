package com.devteam.nutrismart.platform.metabolicadaptation.application.commands;

/**
 * Comando para activar o desactivar la sincronización automática de una conexión wearable.
 *
 * @param id      identificador de la conexión wearable a actualizar
 * @param enabled estado deseado de la sincronización automática
 */
public record SetWearableAutoSyncCommand(Long id, boolean enabled) {}
