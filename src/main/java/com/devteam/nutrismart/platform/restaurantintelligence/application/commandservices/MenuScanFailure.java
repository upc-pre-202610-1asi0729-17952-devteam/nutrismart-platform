package com.devteam.nutrismart.platform.restaurantintelligence.application.commandservices;

public sealed interface MenuScanFailure
        permits MenuScanFailure.InvalidImage,
                MenuScanFailure.UserNotFound,
                MenuScanFailure.PremiumPlanRequired,
                MenuScanFailure.ScanProcessingError,
                MenuScanFailure.RankingFailed {

    record InvalidImage(String reason)         implements MenuScanFailure {}
    record UserNotFound(String userId)         implements MenuScanFailure {}
    record PremiumPlanRequired()               implements MenuScanFailure {}
    record ScanProcessingError(String details) implements MenuScanFailure {}
    record RankingFailed(String reason)        implements MenuScanFailure {}
}
