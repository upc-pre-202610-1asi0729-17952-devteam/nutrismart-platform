package com.devteam.nutrismart.platform.nutritiontracking.application.queries;

/**
 * Consulta para obtener todos los registros de ingesta calórica diaria de un usuario.
 *
 * <p>Representa la intención de recuperar el historial completo de ingestas diarias
 * asociadas a un usuario específico, sin filtrar por fecha, permitiendo obtener
 * una visión longitudinal de su seguimiento nutricional.</p>
 *
 * @param userId Identificador del usuario cuyos registros de ingesta diaria se desean consultar.
 */
public record GetDailyIntakeByUserIdQuery(Long userId) {}
