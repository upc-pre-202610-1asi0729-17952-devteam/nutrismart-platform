package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects;

/**
 * Enumeración que representa el estado de la conexión con un dispositivo wearable externo.
 */
public enum WearableStatus {
    /** El dispositivo wearable está activamente conectado y sincronizando datos. */
    CONNECTED,
    /** El dispositivo wearable no está conectado o la sincronización fue revocada. */
    DISCONNECTED
}
