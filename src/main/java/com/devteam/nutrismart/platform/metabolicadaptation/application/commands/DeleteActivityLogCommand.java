package com.devteam.nutrismart.platform.metabolicadaptation.application.commands;

/**
 * Comando para eliminar un registro de actividad física existente identificado por su {@code id}.
 */
public record DeleteActivityLogCommand(Long id) {}
