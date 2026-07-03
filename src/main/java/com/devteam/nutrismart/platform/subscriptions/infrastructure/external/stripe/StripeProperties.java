package com.devteam.nutrismart.platform.subscriptions.infrastructure.external.stripe;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Propiedades de configuración de Stripe leídas del prefijo {@code stripe.*}
 * (definidas en {@code application-*.properties}).
 * <p>
 * La {@code secretKey} vive únicamente en el backend y nunca debe exponerse al
 * frontend. Cuando está vacía, el gateway opera en modo simulado (no cobra).
 * </p>
 */
@Component
@ConfigurationProperties(prefix = "stripe")
public class StripeProperties {

    /** Clave secreta de Stripe (sk_test_.../sk_live_...). Vacía = modo simulado. */
    private String secretKey = "";

    /** Clave publicable de Stripe (pk_test_.../pk_live_...). Informativa en el backend. */
    private String publishableKey = "";

    public String getSecretKey() { return secretKey; }
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }

    public String getPublishableKey() { return publishableKey; }
    public void setPublishableKey(String publishableKey) { this.publishableKey = publishableKey; }

    /** Indica si Stripe está configurado con una clave secreta utilizable. */
    public boolean isConfigured() {
        return secretKey != null && !secretKey.isBlank();
    }
}
