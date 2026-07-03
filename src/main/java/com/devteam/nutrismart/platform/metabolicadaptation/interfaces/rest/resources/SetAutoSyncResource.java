package com.devteam.nutrismart.platform.metabolicadaptation.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload para activar o desactivar la sincronización automática de una conexión wearable.
 */
public record SetAutoSyncResource(
        @Schema(description = "Whether automatic re-syncing is enabled") boolean enabled
) {}
