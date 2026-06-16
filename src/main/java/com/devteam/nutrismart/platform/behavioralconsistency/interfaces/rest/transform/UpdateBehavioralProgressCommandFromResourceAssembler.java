package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform;

import com.devteam.nutrismart.platform.behavioralconsistency.application.commands.UpdateBehavioralProgressCommand;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.UpdateBehavioralProgressResource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public final class UpdateBehavioralProgressCommandFromResourceAssembler {

    private UpdateBehavioralProgressCommandFromResourceAssembler() {}

    public static UpdateBehavioralProgressCommand toCommandFromResource(Long id, UpdateBehavioralProgressResource resource) {
        List<LocalDate> goalMetDates = resource.goalMetDates() != null
                ? resource.goalMetDates().stream().map(LocalDate::parse).collect(Collectors.toList())
                : null;
        boolean goalMetToday = Boolean.TRUE.equals(resource.goalMetToday());
        return new UpdateBehavioralProgressCommand(
                id,
                resource.adherenceStatus(),
                resource.streak(),
                resource.consecutiveMisses(),
                resource.weeklyCompletionRate(),
                resource.lastEvaluatedAt(),
                goalMetDates,
                goalMetToday);
    }
}
