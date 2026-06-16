package com.devteam.nutrismart.platform.analytics.application.commandservices;

import com.devteam.nutrismart.platform.analytics.application.commands.UpdateAdherenceProgressCommand;
import com.devteam.nutrismart.platform.analytics.application.commands.UpdateDailyDashboardCommand;
import com.devteam.nutrismart.platform.analytics.domain.model.aggregates.Analytics;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Puerto de entrada (interfaz de servicio de comandos) para el módulo de analíticas.
 * Define las operaciones de escritura que pueden desencadenar cambios en el agregado
 * {@link Analytics}. Los implementadores orquestan la lógica de aplicación delegando
 * en puertos de consulta y puertos de acceso a otros contextos.
 */
public interface AnalyticsCommandService {

    /**
     * Procesa el comando para actualizar el panel de control diario de un usuario.
     *
     * @param command comando que contiene el identificador de usuario y la fecha objetivo
     * @return resultado exitoso con el agregado {@link Analytics} generado,
     *         o un fallo tipado si el usuario no existe o los datos son inválidos
     */
    Result<Analytics, AnalyticsCommandFailure> handle(UpdateDailyDashboardCommand command);

    /**
     * Procesa el comando para actualizar el progreso de adherencia de un usuario.
     *
     * @param command comando que contiene el identificador de usuario
     * @return resultado exitoso con el agregado {@link Analytics} actualizado,
     *         o un fallo tipado si el usuario no existe
     */
    Result<Analytics, AnalyticsCommandFailure> handle(UpdateAdherenceProgressCommand command);
}
