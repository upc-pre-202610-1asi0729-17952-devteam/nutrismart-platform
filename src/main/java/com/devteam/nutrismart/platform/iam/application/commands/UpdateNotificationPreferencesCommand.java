package com.devteam.nutrismart.platform.iam.application.commands;

/**
 * Comando para actualizar las preferencias de notificación de un usuario.
 * Cada bandera es nullable para permitir actualizaciones parciales; un valor nulo
 * deja la preferencia correspondiente sin cambios.
 *
 * @param userId               identificador del usuario
 * @param dailyLogReminder     recordatorio diario de registro de comidas
 * @param weeklyProgressReport reporte semanal de progreso
 * @param mealSuggestions      sugerencias de comidas
 * @param streakAlerts         alertas de racha
 * @param marketingEmails      correos de marketing
 */
public record UpdateNotificationPreferencesCommand(
        Long userId,
        Boolean dailyLogReminder,
        Boolean weeklyProgressReport,
        Boolean mealSuggestions,
        Boolean streakAlerts,
        Boolean marketingEmails
) {}
