package com.devteam.nutrismart.platform.restaurantintelligence.application.commandservices;

import com.devteam.nutrismart.platform.restaurantintelligence.application.commands.ScanMenuPhotoCommand;
import com.devteam.nutrismart.platform.restaurantintelligence.domain.model.RankedDishResult;
import com.devteam.nutrismart.platform.shared.application.result.Result;

import java.util.List;

public interface MenuScanCommandService {
    Result<List<RankedDishResult>, MenuScanFailure> handleMenuScan(ScanMenuPhotoCommand command);
}
