package com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices;

/**
 * Interfaz sellada que modela los posibles fallos en la ejecución de comandos de {@code BodyMetric}.
 * Cada variante describe una condición de error específica del dominio.
 */
public sealed interface BodyMetricCommandFailure
        permits BodyMetricCommandFailure.BodyMetricNotFound,
                BodyMetricCommandFailure.InvalidData {

    /** El registro de métricas corporales con el identificador dado no fue encontrado. */
    record BodyMetricNotFound(Long id) implements BodyMetricCommandFailure {}
    /** Los datos del comando son inválidos; el campo {@code reason} describe el motivo. */
    record InvalidData(String reason) implements BodyMetricCommandFailure {}
}
