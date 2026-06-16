package com.devteam.nutrismart.platform.metabolicadaptation.application.queries;

/**
 * Consulta para obtener todas las conexiones wearable,
 * opcionalmente filtradas por el identificador de usuario ({@code userId}).
 * Si {@code userId} es {@code null}, se retornan todos los registros del sistema.
 */
public record GetAllWearableConnectionsQuery(Long userId) {}
