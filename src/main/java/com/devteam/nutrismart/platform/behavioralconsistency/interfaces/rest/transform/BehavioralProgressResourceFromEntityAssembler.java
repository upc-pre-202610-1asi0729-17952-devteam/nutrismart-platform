package com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.transform;

import com.devteam.nutrismart.platform.behavioralconsistency.domain.model.aggregates.BehavioralProgress;
import com.devteam.nutrismart.platform.behavioralconsistency.interfaces.rest.resources.BehavioralProgressResource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ensamblador estático que convierte el agregado de dominio {@code BehavioralProgress}
 * en el recurso de respuesta REST {@code BehavioralProgressResource}.
 */
public final class BehavioralProgressResourceFromEntityAssembler {

    private BehavioralProgressResourceFromEntityAssembler() {}

    /**
     * Convierte un agregado de dominio en su representación REST.
     * Las fechas de objetivo cumplido se proyectan como cadenas ISO {@code YYYY-MM-DD}.
     *
     * @param domain instancia del agregado {@code BehavioralProgress}
     * @return recurso REST {@code BehavioralProgressResource} listo para serializar
     */
    public static BehavioralProgressResource toResourceFromEntity(BehavioralProgress domain) {
        List<String> goalMetDates = domain.getGoalMetDates().stream()
                .map(LocalDate::toString)
                .collect(Collectors.toList());
        return new BehavioralProgressResource(
                domain.getId(),
                domain.getUserId(),
                domain.getAdherenceStatus(),
                domain.getStreak(),
                domain.getConsecutiveMisses(),
                domain.getWeeklyCompletionRate(),
                domain.getLastEvaluatedAt(),
                goalMetDates);
    }
}
