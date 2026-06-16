package com.devteam.nutrismart.platform.iam.application.commands;

import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserPlan;

/**
 * Comando para actualizar el plan de suscripción de un usuario existente.
 *
 * @param userId identificador del usuario cuyo plan se actualizará
 * @param plan   nuevo plan de suscripción a asignar
 */
public record UpdateUserPlanCommand(Long userId, UserPlan plan) {}
