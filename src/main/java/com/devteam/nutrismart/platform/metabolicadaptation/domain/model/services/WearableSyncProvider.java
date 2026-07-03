package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.services;

import java.time.LocalDate;
import java.util.List;

/**
 * Abstracción sobre la API de autorización y obtención de actividad de un proveedor wearable externo.
 * La infraestructura provee la implementación concreta (p. ej. Google Health).
 */
public interface WearableSyncProvider {

    /**
     * Intercambia un código de autorización OAuth por los tokens de acceso y refresco del proveedor.
     *
     * @param oauthCode código de autorización del flujo de consentimiento del proveedor
     * @return el resultado de la autorización, con los tokens en caso de éxito
     */
    WearableAuthorization authorize(String oauthCode);

    /**
     * Intercambia un token de refresco por un token de acceso fresco.
     *
     * @param refreshToken el token de refresco de larga duración concedido previamente
     * @return el resultado de la autorización refrescada; el proveedor puede no devolver un nuevo refresh token
     */
    WearableAuthorization refresh(String refreshToken);

    /**
     * Obtiene las actividades registradas por el proveedor dentro de un rango de fechas.
     *
     * @param accessToken token de acceso válido (no expirado) que autoriza la llamada
     * @param from        límite inferior inclusivo del rango de fechas
     * @param to          límite superior inclusivo del rango de fechas
     * @return las actividades reportadas por el proveedor para el rango
     */
    List<ImportedActivity> fetchActivities(String accessToken, LocalDate from, LocalDate to);
}
