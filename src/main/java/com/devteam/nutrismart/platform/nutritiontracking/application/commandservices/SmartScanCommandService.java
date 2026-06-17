package com.devteam.nutrismart.platform.nutritiontracking.application.commandservices;

import com.devteam.nutrismart.platform.nutritiontracking.application.commands.ConfirmPlateScanCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.commands.ScanPlateCommand;
import com.devteam.nutrismart.platform.nutritiontracking.application.ports.PlateItemResult;
import com.devteam.nutrismart.platform.shared.application.result.Result;

import java.util.List;

/**
 * Servicio de comandos para el escaneo inteligente de platos mediante visión artificial.
 * El escaneo de menús de restaurante ha sido extraído al BC RestaurantIntelligence.
 */
public interface SmartScanCommandService {

    Result<List<PlateItemResult>, SmartScanCommandFailure> handlePlateScan(ScanPlateCommand command);

    Result<Integer, SmartScanCommandFailure> handlePlateScanConfirm(ConfirmPlateScanCommand command);
}
