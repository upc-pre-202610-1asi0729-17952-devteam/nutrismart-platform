package com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.LogBodyMetricsCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.UpdateBodyMetricCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.BodyMetric;
import com.devteam.nutrismart.platform.shared.application.result.Result;

/**
 * Puerto de servicio de aplicación para los comandos de escritura del agregado {@code BodyMetric}.
 * Define las operaciones de creación y actualización de métricas corporales.
 */
public interface BodyMetricCommandService {
    /**
     * Procesa el comando para registrar nuevas métricas corporales de un usuario.
     *
     * @param command comando con los datos del nuevo registro
     * @return {@code Result} con el agregado creado o el fallo correspondiente
     */
    Result<BodyMetric, BodyMetricCommandFailure> handle(LogBodyMetricsCommand command);

    /**
     * Procesa el comando para actualizar un registro de métricas corporales existente.
     *
     * @param command comando con el identificador del registro y los nuevos valores
     * @return {@code Result} con el agregado actualizado o el fallo correspondiente
     */
    Result<BodyMetric, BodyMetricCommandFailure> handle(UpdateBodyMetricCommand command);
}
