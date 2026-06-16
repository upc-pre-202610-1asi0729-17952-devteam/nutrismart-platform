package com.devteam.nutrismart.platform.analytics.infrastructure.acl;

import com.devteam.nutrismart.platform.analytics.application.ports.NutritionSummaryPort;
import com.devteam.nutrismart.platform.nutritiontracking.interfaces.acl.NutritionTrackingContextFacade;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Adaptador ACL que implementa {@link NutritionSummaryPort} delegando en la fachada
 * del contexto de seguimiento nutricional ({@code NutritionTrackingContextFacade}).
 * Traduce las ingestas diarias y registros de comidas al contrato interno del módulo de analíticas.
 */
@Component
public class NutritionSummaryAdapter implements NutritionSummaryPort {

    private final NutritionTrackingContextFacade nutritionTrackingContextFacade;

    public NutritionSummaryAdapter(NutritionTrackingContextFacade nutritionTrackingContextFacade) {
        this.nutritionTrackingContextFacade = nutritionTrackingContextFacade;
    }

    @Override
    public List<DailyIntakeSummaryData> getDailyIntakes(Long userId) {
        return nutritionTrackingContextFacade.getDailyIntakes(userId)
                .stream()
                .map(di -> new DailyIntakeSummaryData(
                        di.userId(),
                        di.date(),
                        di.dailyGoal(),
                        di.consumed(),
                        di.active()
                ))
                .toList();
    }

    @Override
    public List<MealRecordSummaryData> getMealRecords(Long userId) {
        return nutritionTrackingContextFacade.getMealRecords(userId)
                .stream()
                .map(mr -> new MealRecordSummaryData(
                        mr.userId(),
                        mr.loggedAt(),
                        mr.protein(),
                        mr.carbs(),
                        mr.fat()
                ))
                .toList();
    }
}
