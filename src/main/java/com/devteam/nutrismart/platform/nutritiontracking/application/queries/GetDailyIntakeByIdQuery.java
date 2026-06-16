package com.devteam.nutrismart.platform.nutritiontracking.application.queries;

/**
 * Consulta para obtener un registro de ingesta calórica diaria por su identificador.
 *
 * <p>Representa la intención de recuperar un registro específico de ingesta diaria
 * utilizando su clave primaria como criterio de búsqueda.</p>
 *
 * @param id Identificador único del registro de ingesta diaria a consultar.
 */
public record GetDailyIntakeByIdQuery(Long id) {}
