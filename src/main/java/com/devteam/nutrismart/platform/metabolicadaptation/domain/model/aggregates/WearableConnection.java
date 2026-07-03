package com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.WearableStatus;

import java.time.Instant;

/**
 * Agregado raíz que representa la conexión de un usuario con un proveedor wearable externo
 * (p. ej. Google Health).
 * <p>
 * Registra el proveedor, el estado de la conexión, los instantes de última sincronización y
 * autorización, la preferencia de sincronización automática y las credenciales OAuth
 * (access/refresh token) usadas para llamar a la API del proveedor. Los tokens nunca se
 * exponen en los recursos REST.
 * </p>
 */
public class WearableConnection {

    private Long id;
    private Long userId;
    private String provider;
    private WearableStatus status;
    private Instant lastSyncedAt;
    private Instant authorizedAt;
    private boolean autoSyncEnabled;
    private String accessToken;
    private String refreshToken;
    private Instant tokenExpiresAt;

    private WearableConnection() {}

    /**
     * Crea una nueva conexión con un proveedor wearable con validación de invariantes.
     *
     * @param userId          identificador del usuario
     * @param provider        nombre del proveedor del wearable (no puede ser nulo ni vacío)
     * @param status          estado inicial de la conexión; si es {@code null} se asigna {@code DISCONNECTED}
     * @param lastSyncedAt    instante de la última sincronización (opcional)
     * @param authorizedAt    instante de autorización; si es {@code null} se usa el instante actual
     * @param autoSyncEnabled si la conexión debe re-sincronizarse automáticamente
     * @return nueva instancia válida de {@code WearableConnection}
     * @throws IllegalArgumentException si userId es nulo o provider es nulo/vacío
     */
    public static WearableConnection create(Long userId, String provider, WearableStatus status,
                                            Instant lastSyncedAt, Instant authorizedAt, boolean autoSyncEnabled) {
        if (userId == null) throw new IllegalArgumentException("userId must not be null");
        if (provider == null || provider.isBlank()) throw new IllegalArgumentException("provider must not be blank");
        WearableConnection wc = new WearableConnection();
        wc.userId = userId;
        wc.provider = provider;
        wc.status = status != null ? status : WearableStatus.DISCONNECTED;
        wc.lastSyncedAt = lastSyncedAt;
        wc.authorizedAt = authorizedAt != null ? authorizedAt : Instant.now();
        wc.autoSyncEnabled = autoSyncEnabled;
        return wc;
    }

    /**
     * Reconstituye una {@code WearableConnection} desde el almacenamiento de persistencia.
     *
     * @param id              identificador persistido
     * @param userId          identificador del usuario
     * @param provider        nombre del proveedor
     * @param status          estado de la conexión
     * @param lastSyncedAt    instante de la última sincronización
     * @param authorizedAt    instante de autorización
     * @param autoSyncEnabled si la conexión tiene la re-sincronización automática activada
     * @param accessToken     token de acceso del proveedor (puede ser nulo)
     * @param refreshToken    token de refresco del proveedor (puede ser nulo)
     * @param tokenExpiresAt  instante de expiración del token de acceso (puede ser nulo)
     * @return instancia de {@code WearableConnection} reconstituida
     */
    public static WearableConnection rehydrate(Long id, Long userId, String provider, WearableStatus status,
                                               Instant lastSyncedAt, Instant authorizedAt, boolean autoSyncEnabled,
                                               String accessToken, String refreshToken, Instant tokenExpiresAt) {
        WearableConnection wc = new WearableConnection();
        wc.id = id;
        wc.userId = userId;
        wc.provider = provider;
        wc.status = status;
        wc.lastSyncedAt = lastSyncedAt;
        wc.authorizedAt = authorizedAt;
        wc.autoSyncEnabled = autoSyncEnabled;
        wc.accessToken = accessToken;
        wc.refreshToken = refreshToken;
        wc.tokenExpiresAt = tokenExpiresAt;
        return wc;
    }

    /**
     * Almacena los tokens concedidos por el proveedor. Preserva el refresh token actual cuando
     * el proveedor no devuelve uno nuevo (p. ej. en un refresco simple).
     *
     * @param accessToken  token de acceso recién concedido
     * @param refreshToken token de refresco, o {@code null} para conservar el actual
     * @param expiresAt    instante de expiración del token de acceso, o {@code null} si se desconoce
     */
    public void applyAuthorization(String accessToken, String refreshToken, Instant expiresAt) {
        this.accessToken = accessToken;
        if (refreshToken != null && !refreshToken.isBlank()) this.refreshToken = refreshToken;
        this.tokenExpiresAt = expiresAt;
    }

    /**
     * Registra una sincronización exitosa: sella la marca temporal y transiciona a {@code CONNECTED}.
     *
     * @param syncedAt instante en que se completó la sincronización
     */
    public void applySync(Instant syncedAt) {
        this.lastSyncedAt = syncedAt;
        this.status = WearableStatus.CONNECTED;
    }

    /**
     * Activa o desactiva la sincronización automática de esta conexión.
     *
     * @param enabled {@code true} para activar la sincronización automática; {@code false} para desactivarla
     */
    public void setAutoSync(boolean enabled) {
        this.autoSyncEnabled = enabled;
    }

    /**
     * Transiciona la conexión al estado {@code DISCONNECTED}, revocando la sincronización y limpiando los tokens.
     */
    public void applyDisconnect() {
        this.status = WearableStatus.DISCONNECTED;
        this.autoSyncEnabled = false;
        this.accessToken = null;
        this.refreshToken = null;
        this.tokenExpiresAt = null;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getProvider() { return provider; }
    public WearableStatus getStatus() { return status; }
    public Instant getLastSyncedAt() { return lastSyncedAt; }
    public Instant getAuthorizedAt() { return authorizedAt; }
    public boolean isAutoSyncEnabled() { return autoSyncEnabled; }
    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public Instant getTokenExpiresAt() { return tokenExpiresAt; }
}
