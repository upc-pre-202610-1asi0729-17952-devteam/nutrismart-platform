package com.devteam.nutrismart.platform.nutritiontracking.application.queries;

/**
 * Consulta para obtener todos los registros de ingesta calórica diaria del sistema.
 *
 * <p>Representa la intención de recuperar el listado completo de ingestas diarias
 * almacenadas, sin aplicar ningún filtro por usuario ni por fecha. Típicamente
 * utilizada en contextos administrativos o de supervisión general.</p>
 */
public record GetAllDailyIntakesQuery() {}
