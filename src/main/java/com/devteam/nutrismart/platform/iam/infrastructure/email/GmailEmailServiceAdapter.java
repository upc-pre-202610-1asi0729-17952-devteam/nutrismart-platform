package com.devteam.nutrismart.platform.iam.infrastructure.email;

import com.devteam.nutrismart.platform.iam.domain.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * Adaptador de infraestructura que implementa {@link EmailService} usando
 * Spring Mail con soporte para Gmail mediante SMTP.
 * Genera un correo HTML con el enlace de restablecimiento de contraseña y lo envía
 * como mensaje MIME multiparte con codificación UTF-8.
 */
@Component
public class GmailEmailServiceAdapter implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.base-url:http://localhost:4200}")
    private String appBaseUrl;

    @Value("${app.mail.from}")
    private String fromEmail;

    public GmailEmailServiceAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        String resetUrl = appBaseUrl + "/auth/reset-password?token=" + resetToken;
        String html = """
                <div style="font-family:sans-serif;max-width:480px;margin:auto">
                  <h2>Reset your NutriSmart password</h2>
                  <p>Click the button below to set a new password. The link expires in <strong>1 hour</strong>.</p>
                  <a href="%s"
                     style="display:inline-block;padding:12px 24px;background:#22c55e;color:#fff;
                            border-radius:8px;text-decoration:none;font-weight:600">
                    Reset password
                  </a>
                  <p style="margin-top:24px;color:#6b7280;font-size:13px">
                    If you didn't request a password reset, you can safely ignore this email.
                  </p>
                </div>
                """.formatted(resetUrl);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Reset your NutriSmart password");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }
}
