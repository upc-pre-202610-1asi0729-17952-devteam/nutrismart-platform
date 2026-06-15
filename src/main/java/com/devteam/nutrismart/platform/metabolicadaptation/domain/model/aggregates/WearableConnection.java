package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.WearableStatus;

import java.time.Instant;

/**
 * Agregado raíz que representa la conexión de un usuario con un dispositivo wearable externo.
 * <p>
 * Registra el proveedor del dispositivo (p. ej. Fitbit, Garmin), el estado de la conexión
 * y los instantes de última sincronización y autorización.
 * </p>
 */
public class WearableConnection {

    private Long id;
    private Long userId;
    private String provider;
    private WearableStatus status;
    private Instant lastSyncedAt;
    private Instant authorizedAt;

    private WearableConnection() {}

    /**
     * Crea una nueva conexión con un dispositivo wearable con validación de invariantes.
     *
     * @param userId       identificador del usuario
     * @param provider     nombre del proveedor del wearable (no puede ser nulo ni vacío)
     * @param status       estado inicial de la conexión; si es {@code null} se asigna {@code DISCONNECTED}
     * @param lastSyncedAt instante de la última sincronización (opcional)
     * @param authorizedAt instante de autorización; si es {@code null} se usa el instante actual
     * @return nueva instancia válida de {@code WearableConnection}
     * @throws IllegalArgumentException si userId es nulo o provider es nulo/vacío
     */
    public static WearableConnection create(Long userId, String provider, WearableStatus status,
                                            Instant lastSyncedAt, Instant authorizedAt) {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (provider == null || provider.isBlank()) throw new IllegalArgumentException("provider must not be blank");
        WearableConnection wc = new WearableConnection();
        wc.userId = userId;
        wc.provider = provider;
        wc.status = status != null ? status : WearableStatus.DISCONNECTED;
        wc.lastSyncedAt = lastSyncedAt;
        wc.authorizedAt = authorizedAt != null ? authorizedAt : Instant.now();
        return wc;
    }

    /**
     * Reconstituye una {@code WearableConnection} desde el almacenamiento de persistencia.
     *
     * @param id           identificador persistido
     * @param userId       identificador del usuario
     * @param provider     nombre del proveedor
     * @param status       estado de la conexión
     * @param lastSyncedAt instante de la última sincronización
     * @param authorizedAt instante de autorización
     * @return instancia de {@code WearableConnection} reconstituida
     */
    public static WearableConnection rehydrate(Long id, Long userId, String provider, WearableStatus status,
                                               Instant lastSyncedAt, Instant authorizedAt) {
        WearableConnection wc = new WearableConnection();
        wc.id = id;
        wc.userId = userId;
        wc.provider = provider;
        wc.status = status;
        wc.lastSyncedAt = lastSyncedAt;
        wc.authorizedAt = authorizedAt;
        return wc;
    }

    /**
     * Actualiza el estado de conexión y la marca temporal de sincronización del wearable.
     *
     * @param status       nuevo estado de la conexión
     * @param lastSyncedAt nueva marca temporal de sincronización
     */
    public void update(WearableStatus status, Instant lastSyncedAt) {
        this.status = status;
        this.lastSyncedAt = lastSyncedAt;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getProvider() { return provider; }
    public WearableStatus getStatus() { return status; }
    public Instant getLastSyncedAt() { return lastSyncedAt; }
    public Instant getAuthorizedAt() { return authorizedAt; }
}
