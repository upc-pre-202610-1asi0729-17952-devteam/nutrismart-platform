package com.devteam.nutrismart.platform.smartrecommendation.application.commands;

import java.util.List;

public record ImportRecipeSuggestionsCommand(
        List<String> goalTypes,
        List<String> categories,
        int maxRecipesPerGoal
) {}
