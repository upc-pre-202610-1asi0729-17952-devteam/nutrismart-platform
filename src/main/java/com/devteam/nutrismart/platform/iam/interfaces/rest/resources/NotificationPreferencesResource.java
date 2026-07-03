package com.devteam.nutrismart.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Recurso de solicitud REST para actualizar las preferencias de notificación del usuario.
 * Cada campo es nullable para permitir actualizaciones parciales; un valor nulo deja la
 * preferencia correspondiente sin cambios.
 */
@Schema(description = "Payload for updating the user's notification preferences")
public record NotificationPreferencesResource(
        @Schema(description = "Daily meal-log reminder", example = "true")
        Boolean dailyLogReminder,
        @Schema(description = "Weekly progress report", example = "true")
        Boolean weeklyProgressReport,
        @Schema(description = "Meal suggestions", example = "false")
        Boolean mealSuggestions,
        @Schema(description = "Streak alerts", example = "true")
        Boolean streakAlerts,
        @Schema(description = "Marketing emails", example = "false")
        Boolean marketingEmails
) {}
