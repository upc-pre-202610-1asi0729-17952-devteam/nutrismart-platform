package com.devteam.nutrismart.platform.iam.domain.services;

/**
 * Servicio de dominio que abstrae el envío de correos electrónicos transaccionales.
 * Las implementaciones concretas se ubican en la capa de infraestructura.
 */
public interface EmailService {

    /**
     * Envía un correo de restablecimiento de contraseña con el enlace de recuperación.
     *
     * @param toEmail    dirección de correo del destinatario
     * @param resetToken token UUID incluido en el enlace de restablecimiento
     */
    void sendPasswordResetEmail(String toEmail, String resetToken);
}
