package com.devteam.nutrismart.platform.metabolicadaptation.application.commands;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.WearableStatus;

import java.time.Instant;

/**
 * Comando para actualizar el estado y la marca de sincronización de una conexión wearable existente.
 * El campo {@code id} identifica la conexión objetivo de la actualización.
 */
public record UpdateWearableConnectionCommand(
        Long id,
        WearableStatus status,
        Instant lastSyncedAt
) {}
