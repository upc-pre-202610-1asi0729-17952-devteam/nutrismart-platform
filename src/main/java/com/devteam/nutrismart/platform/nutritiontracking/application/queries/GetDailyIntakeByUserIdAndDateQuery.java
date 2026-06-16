package com.devteam.nutrismart.platform.nutritiontracking.application.queries;

import java.time.LocalDate;

/**
 * Consulta para obtener el registro de ingesta calórica diaria de un usuario en una fecha concreta.
 *
 * <p>Permite recuperar el registro nutricional correspondiente a un usuario específico
 * en un día determinado, combinando el identificador de usuario y la fecha como
 * criterios de búsqueda compuestos.</p>
 *
 * @param userId Identificador del usuario cuyo registro de ingesta se desea consultar.
 * @param date   Fecha del registro de ingesta diaria a recuperar.
 */
public record GetDailyIntakeByUserIdAndDateQuery(Long userId, LocalDate date) {}
