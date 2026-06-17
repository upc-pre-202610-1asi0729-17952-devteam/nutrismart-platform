package com.devteam.nutrismart.platform.restaurantintelligence.domain.aggregates;

import com.devteam.nutrismart.platform.restaurantintelligence.domain.model.MenuDishResult;
import com.devteam.nutrismart.platform.restaurantintelligence.domain.model.MenuScanStatus;
import com.devteam.nutrismart.platform.restaurantintelligence.domain.model.RankedDishResult;
import com.devteam.nutrismart.platform.restaurantintelligence.domain.model.RestrictedDishResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregate raíz del BC Restaurant Intelligence.
 * Protege la consistencia del ciclo: scan → analyze → filter → rank.
 *
 * TODO: publicar MenuPhotoProcessed cuando status → ANALYZED
 * TODO: publicar RestrictedDishFlagged por cada plato filtrado
 * TODO: publicar CompatibleDishesRanked cuando status → RANKED
 * TODO: publicar RestaurantMealAnalyzed cuando el usuario confirma un plato
 */
public class RestaurantMenu {

    private final Long userId;
    private final String imageBase64;
    private List<MenuDishResult> dishes;
    private List<RestrictedDishResult> restricted;
    private List<RankedDishResult> ranked;
    private MenuScanStatus status;

    public RestaurantMenu(Long userId, String imageBase64) {
        this.userId       = userId;
        this.imageBase64  = imageBase64;
        this.dishes       = new ArrayList<>();
        this.restricted   = new ArrayList<>();
        this.ranked       = new ArrayList<>();
        this.status       = MenuScanStatus.PENDING;
    }

    public void applyAnalysis(List<MenuDishResult> analyzedDishes) {
        this.dishes = new ArrayList<>(analyzedDishes);
        this.status = MenuScanStatus.ANALYZED;
        // TODO: publish MenuPhotoProcessed
    }

    public void applyFilter(List<RestrictedDishResult> restrictedDishes) {
        this.restricted = new ArrayList<>(restrictedDishes);
        this.status = MenuScanStatus.FILTERED;
        // TODO: publish RestrictedDishFlagged per dish
    }

    public void applyRanking(List<RankedDishResult> rankedDishes) {
        this.ranked = new ArrayList<>(rankedDishes);
        this.status = MenuScanStatus.RANKED;
        // TODO: publish CompatibleDishesRanked
    }

    public Long getUserId()              { return userId; }
    public String getImageBase64()       { return imageBase64; }
    public MenuScanStatus getStatus()    { return status; }
    public List<MenuDishResult> getDishes()          { return Collections.unmodifiableList(dishes); }
    public List<RestrictedDishResult> getRestricted(){ return Collections.unmodifiableList(restricted); }
    public List<RankedDishResult> getRanked()        { return Collections.unmodifiableList(ranked); }
}
