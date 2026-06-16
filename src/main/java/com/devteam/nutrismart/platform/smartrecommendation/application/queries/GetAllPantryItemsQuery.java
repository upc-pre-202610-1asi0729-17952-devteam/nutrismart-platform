package com.devteam.nutrismart.platform.smartrecommendation.application.queries;

/**
 * Consulta para obtener los ítems de despensa, con filtro opcional por identificador de usuario.
 */
public record GetAllPantryItemsQuery(Long userId) {}
