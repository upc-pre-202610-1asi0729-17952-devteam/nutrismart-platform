package com.devteam.nutrismart.platform.nutritiontracking.application.commandservices;

/**
 * Jerarquía de fallos que puede producir el servicio de comandos de importación de alimentos.
 * <p>
 * Es una interfaz sellada ({@code sealed}) cuyos tipos permitidos modelan los distintos
 * escenarios de error que pueden ocurrir durante el proceso de importación desde la API de USDA
 * y el enriquecimiento posterior de los datos nutricionales.
 * </p>
 */
public sealed interface FoodImportFailure
        permits FoodImportFailure.USDAUnavailable,
                FoodImportFailure.EnrichmentFailed,
                FoodImportFailure.InvalidData {

    /** El servicio externo de datos alimenticios (USDA) no está disponible o devolvió un error. */
    record USDAUnavailable(String reason) implements FoodImportFailure {}

    /** El proceso de enriquecimiento de datos mediante IA falló para el lote procesado. */
    record EnrichmentFailed(String reason) implements FoodImportFailure {}

    /** Los datos recibidos de la fuente externa son inválidos y no pueden persistirse. */
    record InvalidData(String reason) implements FoodImportFailure {}
}
