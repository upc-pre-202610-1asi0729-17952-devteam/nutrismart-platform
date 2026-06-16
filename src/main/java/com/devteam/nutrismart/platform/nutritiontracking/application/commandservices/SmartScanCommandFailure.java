package com.devteam.nutrismart.platform.nutritiontracking.application.commandservices;

/**
 * Jerarquía de fallos que puede producir el servicio de comandos de escaneo inteligente.
 * <p>
 * Es una interfaz sellada ({@code sealed}) cuyos tipos permitidos modelan los distintos
 * escenarios de error que pueden ocurrir durante el reconocimiento de imágenes,
 * la clasificación de platos o menús, y la confirmación de registros de comida.
 * </p>
 */
public sealed interface SmartScanCommandFailure
        permits SmartScanCommandFailure.RecognitionFailed,
                SmartScanCommandFailure.RankingFailed,
                SmartScanCommandFailure.InvalidImage,
                SmartScanCommandFailure.ConfirmFailed {

    /** El servicio de reconocimiento de imágenes no pudo identificar el contenido del plato o menú. */
    record RecognitionFailed(String reason) implements SmartScanCommandFailure {}

    /** El servicio de clasificación y puntuación de platos del menú falló durante el procesamiento. */
    record RankingFailed(String reason)     implements SmartScanCommandFailure {}

    /** La imagen proporcionada es nula, vacía o no tiene un formato válido para ser procesada. */
    record InvalidImage(String reason)      implements SmartScanCommandFailure {}

    /** No fue posible crear los registros de comida al confirmar los ítems del escaneo de plato. */
    record ConfirmFailed(String reason)     implements SmartScanCommandFailure {}
}
