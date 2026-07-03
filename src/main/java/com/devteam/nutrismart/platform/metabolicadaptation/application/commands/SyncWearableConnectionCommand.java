package com.devteam.nutrismart.platform.metabolicadaptation.application.commands;

/**
 * Comando para disparar manualmente una sincronización de datos de actividad de una conexión wearable.
 *
 * @param id identificador de la conexión wearable a sincronizar
 */
public record SyncWearableConnectionCommand(Long id) {}
