package com.devteam.nutrismart.platform.metabolicadaptation.application.queries;

/**
 * Consulta para obtener todos los registros de composición corporal,
 * opcionalmente filtrados por el identificador de usuario ({@code userId}).
 * Si {@code userId} es {@code null}, se retornan todos los registros del sistema.
 */
public record GetAllBodyCompositionsQuery(Long userId) {}
