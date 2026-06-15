package com.devteam.nutrismart.platform.metabolicadaptation.application.commands;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.WearableStatus;

import java.time.Instant;

/**
 * Comando para registrar la conexión de un dispositivo wearable externo para un usuario.
 * Transporta los datos necesarios para crear un nuevo agregado {@code WearableConnection}.
 */
public record ConnectWearableCommand(
        Long userId,
        String provider,
        WearableStatus status,
        Instant lastSyncedAt,
        Instant authorizedAt
) {}
